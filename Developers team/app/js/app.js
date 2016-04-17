function init() {
	var overlayEl = document.querySelector('.overlay'),
		signInButton = document.querySelector('.sign_button-sighin'),
		formEl = document.querySelector('.sign_form');

	signInButton.onclick = function(e) {
		e.preventDefault();
		overlayEl.style.display = 'block';
	}

	overlayEl.onclick = function(e) {
		e.preventDefault();
		if(!e.target.closest('.sign_form')) {
			overlayEl.style.display = 'none';
		}
	}
}
window.addEventListener('DOMContentLoaded', init);