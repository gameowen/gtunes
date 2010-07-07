<g:if test="${loginCmd?.hasErrors()}">
  <div id="message" class="errorMessage"><g:renderErrors bean="${loginCmd}" /></div>
</g:if>
<g:formRemote 
	name="loginForm" 
	url="[controller:'user',action:'login']" 
	update="loginBox"
	onComplete="GTunes.onLogin()">
	<div>Username:</div>
	<g:textField name="login"></g:textField>
	<div>Password:</div>
	<g:passwordField name="password"></g:passwordField>			
	<br/>
	<input type="image" src="${createLinkTo(dir:'images', file:'login-button.gif')}" name="loginButton" id="loginButton"/>
</g:formRemote>
