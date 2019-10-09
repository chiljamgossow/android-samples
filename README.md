# Coding Challenge

*If you have any questions, please contact us.*

## Task

Please write an Android app in Java or Kotlin, that contains two screens as described below:

* Login screen
	* text field for email (validate for existence of "@", change underline colors to black/red/blue and make use of the error view)
	* text field for password (validate for non-empty, change underline colors to black/red/blue and make use of the error view)
	* button for login
		* disable on validation failure
		* title
			* "Login" (default)
			* "Cancel" (while request is running)
			* "Try again" (after request has failed)
	* activity indicator (when request is running)
	* error label
		* empty (default)
		* text (localizedMessage from backend, when request has failed)

* Success screen
	* label "hello."
	* navigation bar with back button

* Networking
	* https://kzkucb84m5.execute-api.us-east-1.amazonaws.com/test/authenticate
	* POST `{ "email": "...", "password": "..." }` (fill in values)
	* expect (200, 401 or 500 will be randomly returned by the api, so you will see all the cases in the app)
		* 200: `{ "token": "uuidv4", "message": "Sample greetings message" }`
		* 401: `{ "message": "Sample authentication error message" }`
		* 4xx/5xx (if something really goes wrong)

* Architecture
	* follow the architecture provided in the template app (MVP)

* Notes
	* the provided template is written in Java, but it is prepared for usage of Kotlin as well.
	
**Please send the result zipped via email when you are done.**

### Good luck!
