include "./interfaces/GoogleCloudStorageInterface.iol"
include "file.iol"

main{
    connectRequest.projectId = "jvproject-289212"
    connectRequest.certificateFilePath = "jvproject-289212-ce3b03a915fd.json"

    connect@GoogleCloudStorage(connectRequest)(connectResponse)
    
    readFileRequest.filename = "test.txt"
    readFileRequest.format = "binary"
    readFile@File(readFileRequest)(readFileResponse)
    
    uploadRequest.bucketName = "example_bucket_balint"
    uploadRequest.content = readFileResponse
    uploadRequest.objectName = "test.txt"
    upload@GoogleCloudStorage( uploadRequest  )(  )

}