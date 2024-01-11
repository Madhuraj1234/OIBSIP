(function() {
	'use strict';
	window.addEventListener('load', function() {
		// Fetch all the forms we want to apply custom Bootstrap validation styles to
		var forms = document.getElementsByClassName('needs-validation');
		// Loop over them and prevent submission
		var validation = Array.prototype.filter.call(forms, function(form) {
			form.addEventListener('submit', function(event) {
				if (form.checkValidity() === false) {
					event.preventDefault();
					event.stopPropagation();
				}
				form.classList.add('was-validated');
			}, false);
		});
	}, false);
})();

//SweetAlert for successfull registration
function showSweetAlert(event) {
	event.preventDefault();

	Swal.fire({
		title: 'Congrats! You have successfully registered',
		icon: 'success',
		showCancelButton: false,
		confirmButtonColor: '#3085d6',
		confirmButtonText: 'OK'
	}).then((result) => {
		if (result.isConfirmed) {
			// Perform the form submission after the user clicks "OK"
			document.querySelector('form').submit();
		}
	});
}

//SweetAlert for Bad credentials
$(document).ready(function() {
	var urlParams = new URLSearchParams(window.location.search);
	if (urlParams.has('error')) {
		Swal.fire({
			title: "Bad Credentials!!",
			icon: "error",
			showClass: {
				popup: `
                        animate__animated
                        animate__shakeX
                    `
			},
			hideClass: {
				popup: `
                        animate__animated
                        animate__fadeOutDown
                        animate__faster
                    `
			}
		});
	}
});
