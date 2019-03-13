import hudson.model.*;

println env.JOB_NAME
println env.BUILD_NUMBER

pipeline{
   
   agent any
   stages{
	   stage("init") {
		   steps{
			   script{
				   model_test = load env.WORKSPACE + "/pipeline/module/pipeline_demo_module_json.groovy"
			   }
		   }
	   }
	   stage("Parse json") {
		   steps{
			   script{
				   json_file = env.WORKSPACE + "/testdata/test_json.json"
				   model_test.read_json_file(json_file)
				   println "================================"
				   json_string = '{"NAME":"Anthony","AGE":18,"CITY":"Beijing","GENDER":"male"}'
				   model_test.read_json_file2(json_string)
				   println "================================"
			   }
		   }
	   }
	   /* 写入json文件
	   stage("write json") {
		   steps{
			   script{
				   json_file = env.WORKSPACE + "/testdata/test_json.json"
				   tojson_file = env.WORKSPACE + "/testdata/new_json.json"
				   model_test.write_json_to_file(json_file,tojson_file)
				   println "================================"
				   json_string = '{"NAME":"Anthony","AGE":18,"CITY":"Beijing","GENDER":"male"}'
				   tojson_file = env.WORKSPACE + "/testdata/new_json1.json"
				   model_test.write_json_to_file(json_string,tojson_file)
			   }
		   }
	   }*/
	   
	   //读取properties格式文件，适合java项目
	   stage("read properties") {
		   steps{
			   script{
				   properties_file = env.WORKSPACE + "/testdata/test.properties"
				   model_test.read_properties(properties_file)
				   println "================================"
			   }
		   }
	   }
	   
	   //读取文件，还有个方法是：file_contents = readFile json_file
	   stage("read yaml file") {
		   steps{
			   script{
				   yaml_file = env.WORKSPACE + "/testdata/test.yml"
				   model_test.read_yaml_file(yaml_file)
				   println "=========================="
				   yaml_string = """
                                        age: 18
                                        city: 'Shanghai'
                                        isMale: false
                                        name: 'Lucy'
                                        """
				   model_test.read_yaml_file(yaml_string)
			   }
		   }
	   }
	   
	   /*构建报异常了  写入文件	
	   stage("write into yaml file") {
		   steps{
			   script{
				   def amap = [name: 'Anthony',
							   age : 18,
							   city: 'Beijing',
							   isMale: true
							   ]
				   yaml_file = env.WORKSPACE + "/testdata/new.yml"
				   model_test.write_to_yaml(amap, yaml_file)
				   println "the contents of yaml file are: "
				   model_test.read_yaml_file(yaml_file)
			   }
		   }
	   }*/
	  
	   //判断文件是否存在 fileExists 用法 
	   stage("fileExists") {
		   steps{
			   script{
				   log_file = env.WORKSPACE + "/testdata/c.log"
				   if(fileExists(log_file) == true) {
					   echo("json file is exists")
				   }else {
					   error("here haven't find json file")
				   }
			   }
		   }
	   }
	   
	   //发送邮件用法
	   stage("send mail test") {
		   steps{
			   script {
				   mail to: '944672405@qq.com',
				   cc: '944672405@qq.com',
				   charset:'UTF-8', // or GBK/GB18030
				   mimeType:'text/plain', // or text/html
				   subject: "Running Pipeline: ${currentBuild.fullDisplayName}",
				   body: "Something is wrong with ${env.BUILD_URL}, just for test send mail via pipeline code"
			   }
		   }
	   }
	   
	   //retry 和 sleep的用法
	   stage("retry and sleep") {
		   steps{
			   script{
			   
				   try {
					   retry(3) {
						   println "here we are test retry fuction"
						   sleep 5
						   println 10/0
						   
					   }
				   }catch (Exception e) {
					   println e
				   }
			   }
		   }
	   }
	   
   }

}


