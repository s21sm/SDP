<!DOCTYPE HTML>

<html>
<head>
<link rel="stylesheet" href="style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<link href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="style.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
          <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
		  <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
		  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  		  
          <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
					  <script type="text/javascript">
					  
						$(function() {
							jQuery.get('ingredients.txt', function(data) {
							var availableTags = data.split(', ');	
							availableTags = availableTags.sort();			
							//console.log("The available Tag are "+ availableTags);
						    function split( val ) {
						      return val.split(",");
						    }
						    function extractLast( term ) {
						      return split( term ).pop();
						    }						 
						    $( "#tags" )
						      // don't navigate away from the field on tab when selecting an item
						      .bind( "keydown", function( event ) {
						        if ( event.keyCode === $.ui.keyCode.TAB &&
						            $( this ).autocomplete( "instance" ).menu.active ) {
						          event.preventDefault();
						        }
						      })
						      
						      .autocomplete({
						        minLength: 1,
						        source: function( request, response ) {
						          var term = $.ui.autocomplete.escapeRegex(extractLast(request.term))
						                // Create two regular expressions, one to find suggestions starting with the user's input:
						                , startsWithMatcher = new RegExp("^" + term, "i")
						                , startsWith = $.grep(availableTags, function(value) {
						                    return startsWithMatcher.test(value.label || value.value || value);
						                })
						                // ... And another to find suggestions that just contain the user's input:
						                , containsMatcher = new RegExp(term, "i")
						                , contains = $.grep(availableTags, function (value) {
						                    return $.inArray(value, startsWith) < 0 &&
						                        containsMatcher.test(value.label || value.value || value);
						                });            
						
						            // Supply the widget with an array containing the suggestions that start with the user's input,
						            // followed by those that just contain the user's input.
						            response(startsWith.concat(contains));
						        },
						        focus: function() {
						          // prevent value inserted on focus
						          return false;
						        },			        
						        
						        select: function AutoCompleteSelectHandler(event, ui)
									{               
									    									
						        //alert("Selected: " + ui.item.value + " aka " + ui.item.id);
						          var terms = split( this.value );
						          // remove the current input
						          terms.pop();
						          // add the selected item
						          
						          terms.push( ui.item.value );		          					          
						          		          
						          terms.push( "" );
						          
						          this.value = terms.join( "," );
						         // $("#tags").css('visibility', 'hidden');						          
						          //$("<div>").append(ui.item.value).appendTo($('#tagsdiv')).addClass("selected");						          
						          
						          return false;
						        },						        

						      });
						      
						  });
					});
					  
					  
					</script>
					<link href="images_video/favicon.ico" rel="icon" type="image/x-icon" />
						<title>Smart Kitchen | A place for home made recipes</title>
					
	
	<header style="width: 100%;">
		<div style="height: 80px; background-color: #fff; color: #000000; font-family: 'Montserrat', sans-serif; padding-left: 120px; background-color: #333333 ">		
		<div style="float: left;  padding-top: 0px; width: 100%; height: 100%; ">
		<div style="float: left; display: inline-block; position: relative; width: 220px;">
				<a href="http://smartkitchen-env.elasticbeanstalk.com/"><img src="images_video/logo.png" style="margin: auto; height: 71px; width: 220px"/></a>
		</div>
		<div style="float: left; display: inline-block; position: relative; width: 80%;">
			<nav class="navbar navbar-default">
			  <div class="container-fluid">
			    <!-- Brand and toggle get grouped for better mobile display -->
			    <div class="navbar-header">
			      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			        <span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			      </button>
			      <a class="navbar-brand" href="#"></a>
			    </div>
			
			    <!-- Collect the nav links, forms, and other content for toggling -->
			    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			      <ul class="nav navbar-nav">
			        <li><a href="About_us.jsp">About us</a></li>
			        <li><a href="Contact_us.jsp">Contact Us</a></li>			        
			        <li><a href="developer.jsp">Developer</a></li>
			      </ul>
			      
			      
			    </div><!-- /.navbar-collapse -->
			  </div><!-- /.container-fluid -->
			</nav>
		</div>	
		</div>		
		</div>
					
		
	</header>	
	</head>



	
	