<HTML>
<HEAD><TITLE>Welcome</TITLE></HEAD>
<BODY>
<H3>Welcome!</H3>
<P>Today is <%= new java.util.Date() %>. 
<%= getQuarter(3) %>
<%!

public String getQuarter(int i){
String quarter;
switch(i){
        case 1: quarter = "Winter";
        break;

        case 2: quarter = "Spring";
        break;

        case 3: quarter = "Summer 69";
        break;

        case 4: quarter = "Summer II";
        break;

        case 5: quarter = "Fall";
        break;

        default: quarter = "ERROR";
}

return quarter;
}

%>
</BODY>
</HTML>
