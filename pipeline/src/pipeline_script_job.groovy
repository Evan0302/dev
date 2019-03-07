import hudson.model.*;
 
println env.JOB_NAME
println env.BUILD_NUMBER
println env.WORKSPACE
 
pipeline{
	agent any
	stages{
		stage("init") {
			steps{
				script{
					model_test = load env.WORKSPACE + "/pipeline/module/pipeline_demo_module.groovy"
					println model_test.toString()
				}
			}
		}
		stage("Test Method") {
			steps{
				script{
					log_files = model_test.find_files('**/*.log')
					
				}
			}
		}
	}
}
 
 
