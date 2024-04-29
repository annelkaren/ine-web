package com.svi.app.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GCSManage {

	public static void uploadObject(String imageName) throws IOException {
		// The ID of your GCP project
		String projectId = "striped-sight-179217";

		// The ID of your GCS bucket
		String bucketName = "ventanilla-web-bucket";

		// The ID of your GCS object
		String objectName = "ine/" + imageName;

		Path path = Paths.get(System.getProperty("user.home"), "ine", "files");
		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

		// Optional: set a generation-match precondition to avoid potential race
		// conditions and data corruptions. The request returns a 412 error if the
		// preconditions are not met.
		Storage.BlobWriteOption precondition;
		if (storage.get(bucketName, objectName) == null) {
			// For a target object that does not yet exist, set the DoesNotExist
			// precondition.
			// This will cause the request to fail if the object is created before the
			// request runs.
			precondition = Storage.BlobWriteOption.doesNotExist();
		} else {
			// If the destination already exists in your bucket, instead set a
			// generation-match
			// precondition. This will cause the request to fail if the existing object's
			// generation
			// changes before the request runs.
			precondition = Storage.BlobWriteOption.generationMatch(storage.get(bucketName, objectName).getGeneration());
		}
		storage.createFrom(blobInfo, path.resolve(imageName), precondition);

		System.out.println("File uploaded to bucket " + bucketName + " as " + objectName);
	}

	public static Blob downloadObject(String name) {
		// The ID of your GCP project
		String projectId = "striped-sight-179217";

		// The ID of your GCS bucket
		String bucketName = "ventanilla-web-bucket";

		// The ID of your GCS object
		String objectName = "ine/" + name;

		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

		Blob blob = storage.get(BlobId.of(bucketName, objectName));
		// The path to which the file should be downloaded
		blob.downloadTo(Paths.get(System.getProperty("user.home"), "ine", "files").resolve(name));

		System.out
				.println("Downloaded object " + objectName + " from bucket name " + bucketName + " to local repo");
		return blob;
	}
}