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
      sh './mvnw org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=false'
    }
	}
  stage('Code Metrics') {
    dir('user-service') {
      sh './mvnw sonar:sonar'
    }
	}
}
