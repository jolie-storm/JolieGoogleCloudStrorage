from file import File
from  "./packages/google_cloud_storage" import GoogleCloudStorage



service TestService{

embed File as File 
embed GoogleCloudStorage  as GoogleCloudStorage

main{
    connectRequest.projectId = "jvproject-289212"
    connectRequest.certificateFilePath ="jvproject-289212-ce3b03a915fd.json"

    connect@GoogleCloudStorage(connectRequest)(connectResponse)
    
    readFileRequest.filename = "test.txt"
    readFileRequest.format = "binary"
    readFile@File(readFileRequest)(readFileResponse)
    
    uploadRequest.bucketName = "example_bucket_balint"
    uploadRequest.content = readFileResponse
    uploadRequest.objectName = "test.txt"
    upload@GoogleCloudStorage( uploadRequest  )(  )

}


}
