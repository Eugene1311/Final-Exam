function init() {
    var overlayEl = document.querySelector('.overlay'),
        signInButton = document.querySelector('.sign_button-sighin'),
        languagesEl = document.querySelector('.languages'),
        engButton = document.getElementById('engButton'),
        signUpButton = document.querySelector('.sign_button-sighup');

    signInButton.onclick = function(e) {
        e.preventDefault();
        overlayEl.style.display = 'block';
    };

    overlayEl.onclick = function(e) {
        e.preventDefault();
        if(!e.target.closest('.sign_form')) {
            overlayEl.style.display = 'none';
        }
    };

    languagesEl.onclick = function(e) {
        e.preventDefault();
        var language;
        
        if(e.target === engButton) {
            language = 'en';
        } else {
            language = 'ru';
        }

        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/localize?language=' + language, true);
        xhr.send();
        xhr.onreadystatechange = function() {
            if (this.readyState != 4) return;

            if (xhr.status !== 200) {
                console.log(xhr);
            }

            console.log(JSON.parse(xhr.responseText));
            Array.prototype.forEach.call(languagesEl.querySelectorAll('.header_button'), function(item) {
                item.classList.remove('languages-selected');
            });
            e.target.classList.add('languages-selected');
        };
    };

    signUpButton.onclick = function(e) {
        e.preventDefault();

        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/login', true);
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(
            JSON.stringify({
                test: "test"
            })
        );
        xhr.onreadystatechange = function() {
            if (this.readyState != 4) return;

            if (xhr.status !== 200) {
                console.log(xhr);
                return;
            }

            console.log(JSON.parse(xhr.responseText));
        };
    }
}
window.addEventListener('DOMContentLoaded', init);