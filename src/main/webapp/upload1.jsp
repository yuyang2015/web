<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>upload</title>
<link rel="stylesheet"
	href="//ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/themes/base/jquery-ui.css"
	type="text/css" />
<link rel="stylesheet"
	href="js/jquery.plupload.queue/css/jquery.plupload.queue.css"
	type="text/css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript"
	src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/plupload.full.min.js"></script>
<script type="text/javascript"
	src="js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
	<script type="text/javascript"
	src="js/spark-md5.js"></script>
</head>

<body style="font: 13px Verdana; background: #eee; color: #333">

	<h1>jQuery UI Widget</h1>

	<p>
		You can see this example with different themes on the <a
			href="http://plupload.com/example_jquery_ui.php">www.plupload.com</a>
		website.
	</p>

	<div id="uploader">
		<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
	</div>
	<br />

	<script type="text/javascript">
		$('#uploader').pluploadQueue({
			url : 'upload!upload',
			filters : {
				// Maximum file size
				max_file_size : '1000mb',
				chunk_size : '1mb',
				unique_names : true,
				// Specify what files to browse for
				mime_types : [ {
					title : "Image files",
					extensions : "jpg,gif,png"
				}, {
					title : "Bam files",
					extensions : "bam"
				}, {
					title : "Zip files",
					extensions : "zip"
				} ]
			},
			init : {
				FilesAdded: function(up, files) {
	              plupload.each(files, function(file) {
	              	alert(file.name);
	              });
	            },
				BeforeUpload:function(up, files){
					var fileReader = new FileReader();
					var spark = new SparkMD5.ArrayBuffer();
					fileReader.readAsArrayBuffer(files.getNative());
					fileReader.onload=function(e){
						spark.append(e.target.result);
						md5 = spark.end();
						up.settings.multipart_params ={"md5":md5};
						alert(md5);
					};
					alert(files.name);
				}
			},
			rename : true,
			flash_swf_url : 'js/Moxie.swf',
			silverlight_xap_url : 'js/Moxie.xap',
		});
	</script>
</body>
</html>
