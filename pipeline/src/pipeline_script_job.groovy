import hudson.model.*;
 
println env.JOB_NAME
println env.BUILD_NUMBER
 
pipeline{
	
	agent any
	stages{
		stage("init") {
			steps{
				script {
					module_test = load env.WORKSPACE + "/pipeline/module/pipeline_demo_module.groovy"
					println "1 + 1 = 2"
				}
			}
		}
	}
	post{
	    failure {
	        script {
	            module_test.send_email_results("Failed","Master","944672405@qq.com,dypfan@163.com")
	        }
	    }
	    success {
	        script {
	            module_test.send_email_results("Success","Master","944672405@qq.com,531861531@qq.com")
	        }
	    }
	}
}
