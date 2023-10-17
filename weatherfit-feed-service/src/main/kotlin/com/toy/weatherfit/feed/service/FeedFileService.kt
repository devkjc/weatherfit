package com.toy.weatherfit.feed.service

import com.toy.weatherfit.feed.domain.FeedFile
import com.toy.weatherfit.feed.domain.FeedFileRepository
import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.File

@Service
class FeedFileService(
    private val feedFileRepository: FeedFileRepository
) {

    fun uploadFile(request: MultipartHttpServletRequest): FeedFile {

        val file: MultipartFile = request.getFile("file")
            ?: throw IllegalArgumentException("파일 업로드에 실패했습니다.")

        val originalFilename = file.originalFilename
            ?: throw IllegalArgumentException("파일 이름을 찾을 수 없습니다.")

        val fileName = getRandomFileName(extractExtension(originalFilename))
        val filePath = "/Users/jongchan/weatherfit/upload/image/$fileName"
        val savedFile = saveFile(file, filePath)
        val thumbnailPath = uploadAndCreateThumbnail(savedFile, fileName)

        return feedFileRepository.save(
            FeedFile(
                originalFileName = originalFilename,
                fileName = fileName,
                filePath = filePath,
                thumbnailPath = thumbnailPath
            )
        )
    }

    private fun saveFile(multipartFile: MultipartFile, filePath: String): File {
        val file = File(filePath)
        file.writeBytes(multipartFile.bytes)
        return file
    }

    private fun extractExtension(originalFilename: String): String {
        val lastIndexOf = originalFilename.lastIndexOf('.')
        return if (lastIndexOf == -1) {
            throw IllegalArgumentException("확장자 명을 찾을 수 없습니다.")
        } else {
            originalFilename.substring(lastIndexOf + 1)
        }
    }

    fun getRandomFileName(extension: String): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..20)
            .map { allowedChars.random() }
            .joinToString("") + ".$extension"
    }

    private fun uploadAndCreateThumbnail(outputFile: File, fileName: String): String {
        val thumbnailPath = "/Users/jongchan/weatherfit/upload/thumbnail/$fileName"

        Thumbnails.of(outputFile)
            .size(200, 200)
            .toFile(thumbnailPath)

        return thumbnailPath
    }
}
