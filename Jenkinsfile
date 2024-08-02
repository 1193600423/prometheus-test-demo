pipeline {
    agent any
    stages {
        stage('Clone Code') {
            steps {
                echo "1.Git Clone Code"
                git url: "https://gitee.com/zhao1193600423/prometheus-test-demo.git"
            }
        }

        stage('Maven Build') {
            steps {
                script {
                    docker.image('maven:latest').inside("-v /root/.m2:/root/.m2") {
                        echo "2.Maven Build Stage"
                        sh "mvn clean install -Dmaven.test.skip=true"
                    }
                }
            }
        }
        stage('Image Build') {
            steps {
                echo "3.Image Build Stage"
                sh 'docker build -f Dockerfile --build-arg jar_name=target/prometheus-test-demo-0.0.1-SNAPSHOT.jar -t prometheus-test-demo:${BUILD_ID} . '
                sh 'docker tag  prometheus-test-demo:${BUILD_ID}  harbor.edu.cn/nju09/prometheus-test-demo:${BUILD_ID}'
            }
        }
        stage('Image Push') {
            steps {
                echo "4.Push Docker Image Stage"
                sh "docker login --username=nju09 harbor.edu.cn -p cloudnju09"
                sh "docker push harbor.edu.cn/nju09/prometheus-test-demo:${BUILD_ID}"
            }
        }

        stage('Clone Git Repository') {
            steps {
                node('slave') {
                    container('jnlp-kubectl') {
                        echo "5. Git Clone YAML To Slave"
                        git url: "https://gitee.com/zhao1193600423/prometheus-test-demo.git"

                        echo "6. Change YAML File Stage"
                        sh 'sed -i "s#{VERSION}#${BUILD_ID}#g" ./jenkins/scripts/prometheus-test-demo.yaml'

                        echo "7. Deploy To K8s Stage"
                        sh 'kubectl apply -f ./jenkins/scripts/prometheus-test-demo.yaml'

                        echo "8. Deploy ServiceMonitor To K8s"
                        sh 'kubectl apply -f ./jenkins/scripts/prometheus-test-serviceMonitor.yaml'
                    }
                }
            }
        }
    }
}
