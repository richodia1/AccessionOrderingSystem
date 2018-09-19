var wp = google.gears.workerPool;

wp.onerror = function(message) {
	wp.sendMessage("" + message.lineNumber + ": " + message.message, 0);
	wp.sendMessage({errorMessage: message}, 0);
	return true;
};

wp.onmessage = function(a, b, message) {
	if (message.body.file != null) {
		uploadFile(message.body.file, message.body.postUrl);
	}
	if (message.body.abort) {
		if (wp.httpUploadRequest!=null) {
			wp.httpUploadRequest.abort();
			wp.sendMessage({aborted: true}, 0);
			wp.httpUploadRequest=null;
		}
	}
}

function uploadFile(file, postUrl) {
	wp.sendMessage({ fileName: file.name, startingUpload: true }, 0);
	if (wp.httpUploadRequest==null)
		wp.httpUploadRequest = google.gears.factory.create("beta.httprequest");
	else
		throw new Error("Can only handle one file at a time");
	wp.httpUploadRequest.open("PUT", postUrl);
	wp.httpUploadRequest.setRequestHeader("gears-filename", file.name);
	wp.httpUploadRequest.setRequestHeader("gears-contentType", file.metaData.mimeType);
	wp.httpUploadRequest.upload.onprogress = function _onprogress(pro) {
		wp.sendMessage({ progress: pro }, 0);
	};
	
	wp.httpUploadRequest.onreadystatechange = function() {
		//wp.sendMessage("Ready state" + httpUploadRequest.readyState, 0);
		switch (wp.httpUploadRequest.readyState) {
		case 4: //complete
			wp.sendMessage({ uploadComplete: true, name: file.name }, 0);
			wp.httpUploadRequest=null;
			break;
		}
	};
	wp.httpUploadRequest.send(file.blob);
}

