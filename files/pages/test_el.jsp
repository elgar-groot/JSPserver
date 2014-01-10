<html>
<head>
<title>Testing jsp page</title>
</head>
<%-- Declarations. This comment should not be in the HTML file --%>
<%! String user = "Elgar"; %>
<%! int age = 21; %>
<body>
	<h2>Hi <%= user %> of age <%= age 
	%> </h2>
	<p>Look, I can count to your age:</p>
	<ul>
	<% for(int i = 1; i<=age; i++){ %>
		<li> <b> <%= i %> </b> </li>
	<% } %>
	</ul>	
</body>
</html>