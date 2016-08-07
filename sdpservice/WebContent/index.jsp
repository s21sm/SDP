<jsp:include page="header.jsp" />
		<div id="container">
			<div style="width: 100%; min-height: 700px;" id="navi">
					
				<img src="images_video/front.JPG" STYLE="width: 100%;">
				    
					<!--<video poster="image" preload="true" autoplay loop muted>
					     <source src="images_video/home_video.mp4"  type="video/mp4" />
					     Your browser does not support the video tag.
				    </video>
				       -->
			</div>
		</div>
	<div class="body">
		<div class="veryTop" style="color: #fffffff; padding-top: 50px;">
			<div style="opacity: 1; position: relative; text-align: center;">
			<div style="top: 40%;"><br/><br/><br/><br/><br/><br/></div>
					<div style="margin-top: 30%; margin: auto; font-size: 7em; color: #FFFFFF; width: 60%; opacity: 1; font-family: 'Montserrat', sans-serif; position: relative;">
					Smart Kitchen 
					</div>
					<div style="font-size: 20px; margin: auto; font-weight: bold; width: 60%; font-family: 'Montserrat', sans-serif;">
					Search From Over 1,000,000 Recipes.
					</div>
			</div>
			<br/>
		</div>


			  <div class="form">
					<form action="result" method="POST">
						<div class="innerbody" style="margin: auto;">
							<div style="width: 100%; position: relative;height: 32px;margin-top: 30px; display: inline-block;" >
							<div Id = "container2">
								<div  name="ingredientdiv" class="box" id="tagsdiv"></div>							
								<input type="text" name="ingredient1" class="box" placeholder="e.g chicken,butter,milk" id="tags">
							</div>
							<!--<input type="text" name="ingredient2" class="boxes" placeholder="ingredient">
							<input type="text" name="ingredient3" class="boxes" placeholder="ingredient">
							<input type="text" name="ingredient4" class="boxes" placeholder="ingredient">
							-->
							<input type="submit" value = "Search" id="button"/>
							</div>
							
							<div  style="text-align: left; margin-top: 4px; margin-top: 14px;">
								<!--<div style="font-weight: bold; font-size: 15px;">Select Your Preferences</div> -->
								<!--
									<div style="float: left;"><input type="radio" name="rdio">General</div>
									<div style="float: left;"><input type="radio" name="rdio">Holiday Food</div>
									<div style="float: left;"><input type="radio" name="rdio">Dessert<br/></div>	
									-->
									<div style="float:left; margin-right: 10px; margin-top: 3px; font-weight: 300;">Need a traditional dish? <a href="" style="color: #FFFFFF" class="link1" data-toggle="modal" data-target="#popmodal"></a></div>
									<div style="color:#000000; width: 100px; display: inline-block; position: relative;">
										<select class="form-control" style="width: 120px; height: 30px;" name="culture">
											
											<option value="general">Select</option>
											<option value="iran">Norooz Iran</option>
											<option value="china">Dragon boat festival China</option>
											<option value="japan">Sea day Japan</option>
											<option value="bangladesh">Pohela Boishakh Bangladesh</option>						
											<option value="pakistan">Eid-ul-fitar Pakistan </option>										
											<option value="finland">Juhannus Paiva Finaland</option>										
											<option value="nepal">Krishna Janmashtami Nepal </option>											
											<option value="ukraine">Tatiana Day Ukraine</option>										
											<option value="nigeria">New Yam Festival Nigeria</option>
											<option value="india">Dewali</option>												

											
											
											
										</select>
									</div>					
							</div>
						</div>						
					</form>
					
				</div>

			<div class="otherbody">
				<div style="float: left; width: 85%;">&nbsp</div>
				<!--<div id="homebanner"><img alt="" src="images_video/animation.gif"></div>  -->
			</div>		
															
			</div>
			<!--
			<div id="popmodal" class="modal fade" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">Traditional Choices</h4>
								</div>
								<div class="modal-body">
									<p>This contains the choices for the traditional recipes</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								</div>
							</div>
						</div>
			-->
 		<jsp:include page="footer.jsp" />							
	</body>
</html>