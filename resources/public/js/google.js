function signOut() {
  var auth2 = gapi.auth2.getAuthInstance();
  auth2.signOut();
}

function onSignIn(googleUser) {
  var id_token = googleUser.getAuthResponse().id_token;
}

var xhr = new XMLHttpRequest();
xhr.open('POST', 'http://localhost:3000');
xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
xhr.onload = function() {
    console.log('Signed in as: ' + xhr.responseText);
};
xhr.send('idtoken=' + id_token);
