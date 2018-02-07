properties([authorizationMatrix(['com.cloudbees.plugins.credentials.CredentialsProvider.Create:rcschrg', 'com.cloudbees.plugins.credentials.CredentialsProvider.Delete:rcschrg', 'com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains:rcschrg', 'com.cloudbees.plugins.credentials.CredentialsProvider.Update:rcschrg', 'com.cloudbees.plugins.credentials.CredentialsProvider.View:rcschrg', 'hudson.model.Item.Build:rcschrg', 'hudson.model.Item.Cancel:rcschrg', 'hudson.model.Item.Configure:rcschrg', 'hudson.model.Item.Delete:rcschrg', 'hudson.model.Item.Discover:rcschrg', 'hudson.model.Item.Move:rcschrg', 'hudson.model.Item.Read:anonymous', 'hudson.model.Item.Read:rcschrg', 'hudson.model.Item.Workspace:rcschrg', 'hudson.model.Run.Delete:rcschrg', 'hudson.model.Run.Replay:rcschrg', 'hudson.model.Run.Update:rcschrg', 'hudson.scm.SCM.Tag:rcschrg'])])

pipeline {
  agent {
    docker {
      image 'openjdk:8'
    }
    
  }
  stages {
    stage('Build') {
      steps {
        sh '''chmod -R 777 .
bash ./gradlew assemble'''
      }
    }
    stage('Test') {
      steps {
        sh 'bash ./gradlew test'
      }
    }
    stage('Sonar') {
      steps {
        withCredentials([string(credentialsId: 'SonarExecutor', variable: 'SONAR_TOKEN')]) {
          sh 'bash ./gradlew jacocoTestReport sonarqube -Dsonar.projectKey=gdx-surface -Dsonar.projectName=xue-$BRANCH_NAME -Dsonar.host.url=https://sonar.rschrage.org -Dsonar.login=$SONAR_TOKEN'
        }
      }
    }
  }
}
