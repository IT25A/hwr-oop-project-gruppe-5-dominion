package hwr.oop.examples.template.service

import hwr.oop.examples.template.service.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ControllerAdvice {

	//https://imgs.search.brave.com/DuICWRwS6BSl4yTwpwdCBZqo1Rl9QPdnWmUNQj-8zAA/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tZWRp/YS50ZW5vci5jb20v/a2VBRUwxTlZSM3dB/QUFBTS9jaGlsbGd1/eS5naWY.jpeg

	@ExceptionHandler(Exception::class)
	fun handleGenericException(
		ex: Exception,
		request: WebRequest,
	): ResponseEntity<ErrorResponse> {
		val errorResponse = ErrorResponse(
			/*status =*/ HttpStatus.INTERNAL_SERVER_ERROR.value(),
			/*error =*/ HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
			/*message =*/ ex.message ?: "An unexpected error occurred",
		)
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(errorResponse)
	}
	
}

