import hudson.model.*;
 
 
def find_files(filetype) {
	
	def files = findFiles(glob:filetype)
	for (file in files) {
		println file.name
	}
}


def send_email_results(status,GITBranch,to_email_address_list) {
	def fileContents = readFile env.WORKSPACE + '/testdata/basic_style.css'
	def subject = "Jenkins Job : " + env.JOB_NAME + "/" + env.BUILD_ID + " has " +  status
	def result_url = env.BUILD_URL + "console"
	
 
	def text = """
  <html>
  <style type="text/css">
  <!--
  ${fileContents}
  -->
  </style>
  <body>
  <div id="content">
  <h1>Summary</h1>
  <div id="sum2">
      <h2>Jenkins Build</h2>
      <ul>
      <li>Job URL : <a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></li>
       <li>Build Result URL : <a href='${result_url}'>${result_url}</a></li>
      </ul>
  </div>
  <div id="sum0">
  <h2>GIT Branch</h2>
  <ul>
  <li>${GITBranch}</li>
  </ul>
  </div>
  </div></body></html>
  """
 
	mail body: text, subject: subject,  mimeType: 'text/html', to: to_email_address_list
}

 
return this;
