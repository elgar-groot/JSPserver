<HTML>
<BODY>
Hier staat zomaar wat tekst
<p>Dit is een test</p>
<%
System.out.println( "Evaluating date now" );
java.util.Date date = new java.util.Date();
System.out.println("Testing the = operator:");
%> <%= date %>

<P><B> Today is <%= new java.util.Date() %>.<br/> Have a nice day! </B></P>
</BODY>
</HTML>