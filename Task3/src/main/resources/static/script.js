//SweetAlert for successfully adding daily task
function showSweetAlert(event) {
	event.preventDefault();

	Swal.fire({
		title: 'Congrats! You have successfully added your task',
		icon: 'success',
		showCancelButton: false,
		confirmButtonColor: '#3085d6',
		confirmButtonText: 'OK'
	}).then((result) => {
		if (result.isConfirmed) {
			document.querySelector('form').submit();
		}
	});
}

//SweetAlert for successfully adding daily task
function showSweetAlert1(event) {
	event.preventDefault();

	Swal.fire({
		title: 'Congrats! You have successfully updated your task',
		icon: 'success',
		showCancelButton: false,
		confirmButtonColor: '#3085d6',
		confirmButtonText: 'OK'
	}).then((result) => {
		if (result.isConfirmed) {
			document.querySelector('form').submit();
		}
	});
}

