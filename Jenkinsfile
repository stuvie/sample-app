node {
  stage('Checkout') {
      checkout scm
  }
	stage('Build') {
	  dir('user-service') {
      sh './mvnw clean compile'
    }
	}
	stage('Unit Test') {
    dir('user-service') {
      sh './mvnw test'
    }
	}
  stage('Integration Test') {
    dir('user-service') {
      sh './mvnw test'
    }
	}
}
