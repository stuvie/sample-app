node {
  stage('Checkout') {
      checkout scm
  }
	stage('Build') {
	  dir('user-service') {
      sh './mvnw clean compile'
    }
	}
	stage('Unit Tests') {
    dir('user-service') {
      sh './mvnw test'
    }
	}
  stage('Integration Tests') {
    dir('user-service') {
      sh './mvnw verify'
    }
	}
  stage('Sonar Analysis') {
    dir('user-service') {
      sh './mvnw sonar:sonar'
    }
	}
}
