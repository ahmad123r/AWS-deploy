pipeline {
    agent any

    environment {
        DOCKER_IMAGE   = "ahmad201218/finalversion:latest"
        EC2_HOST       = "13.49.73.162"
        REMOTE_USER    = "ubuntu"
        CONTAINER_NAME = "my_app"
        HOST_PORT      = "8080"
        CONTAINER_PORT = "8080"

        REGISTRY = "ahmad201218"  // Docker Hub username
        IMAGE_NAME = "finalversion"
        IMAGE_TAG = "latest"
    }

    stages {
          stage('Pull Latest Code') {
            steps {
                // Make sure Jenkins pulls the latest code from your source repository
                git branch: 'main', url: 'https://github.com/ahmad123r/AWS-deploy.git'
            }}
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    docker push ${DOCKER_IMAGE}
                    """
                }
            }
        }

        stage('Deploy on EC2') {
            steps {
                echo "Entering SSH stage..."
                withCredentials([sshUserPrivateKey(credentialsId: 'EC2_PRIVATE_KEY', keyFileVariable: 'SSH_KEY', usernameVariable: 'SSH_USER')]) {
                    sh """
                    ssh -i "$SSH_KEY" -o StrictHostKeyChecking=no $SSH_USER@$EC2_HOST " \
                        echo 'Connected to EC2'; \
                        docker stop my_app || true; \
                        docker rm my_app || true; \
                        docker pull ${DOCKER_IMAGE}; \
                        docker run -d --name ${CONTAINER_NAME} -p ${HOST_PORT}:${CONTAINER_PORT} ${DOCKER_IMAGE}; \
                        echo 'Deployment completed successfully' \
                    "
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Deployment successful!"
        }
        failure {
            echo "Deployment failed!"
        }
    }
}
