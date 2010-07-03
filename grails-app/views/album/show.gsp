<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main">
    <title>gTunes Store</title>
  <g:javascript library="prototype"></g:javascript>
</head>
<body id="body">
  <h1>Online store</h1>
  <h2>Album: ${album.title}</h2>
  <g:render template="album" model="[ album: album, artist: album.artist ]"></g:render>
  <div style="margin-top:10px">
    <g:link controller="store" action="shop">Back to Store</g:link>
  </div>
</body>
</html>
