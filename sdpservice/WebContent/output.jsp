
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hp.hpl.jena.rdf.model.Resource"%>
<%!@SuppressWarnings("unchecked")%>
<%
	pageContext.setAttribute("newLineChar", "\n");
%>


<jsp:include page="header.jsp" />



<body style="width: 100%; position: relative; display: inline-block; ">
	<div style="background-color: #ffffff;width: 100%; position: relative; display: inline-block;">

		<br />


		<div
			style="display: inline-block; position: relative; margin-top: 70px; width: 100%; text-align: center;">
			<img src="images_video/gen_banner.png" style="margin: auto" />
		</div>


		<%
			int st = (Integer) request.getAttribute("status");
		%>
		<%
			if (st == 1) {
		%>


		<%
			String culture = (String) request.getAttribute("culture");

				ArrayList<ArrayList<String>> recipe_details = (ArrayList<ArrayList<String>>) request
						.getAttribute("recipe");
				ArrayList<ArrayList<ArrayList<String>>> ingredient_details = (ArrayList<ArrayList<ArrayList<String>>>) request
						.getAttribute("ingredientInfo");

				ArrayList<HashMap<String, String>> rep_info = (ArrayList<HashMap<String, String>>) request
						.getAttribute("reInfo");

				ArrayList<String> recipe_name = recipe_details.get(0);
				ArrayList<String> recipe_image_url = recipe_details.get(1);
				ArrayList<String> recipe_video_url = recipe_details.get(2);
				ArrayList<String> recipe_instruction = recipe_details.get(3);
				ArrayList<String> recipe_note = recipe_details.get(4);
		%>
	
			
	
		<div style="display: inline-block; position:relative; width: 65%; float: left; margin-right: 20px; margin-left: 50px;">
		<div id = "result"> <%=recipe_name.size()%> <span>Results Found</span></div>
		<%
			for (int i = 0; i < recipe_name.size(); i++) {
		%>





		<div
			style="float: left; width: 100%;  text-align: center; border: 1px #cccccc solid; box-shadow: 6px 6px 3px #cccccc;">
			<br />
			<div
				style="width: 90%; text-align: center; margin: auto; margin-bottom: 20px; display: inline-block; position: relative;">
				
				<div style="float: left;">
					<!-- Recipe Photo: -->
					<img src="<%=recipe_image_url.get(i)%>" alt="" height="200"
						width="200" style="border-radius: 60px;" />
				</div>
				<div
					style="font-size: 23px; padding: 20px; display: inline-block; position: relative; width: 70%; text-align: center;">
					<div
					style="display: inline-block; position: relative; margin-top: 0px; font-size: 26px; width: 45px; height: 45px; border-radius: 30px; background-color: #ff6600; color: #FFFFFF">
					<%=i + 1%>
				</div>
				<br/><br/>
					<div
						style="margin-bottom: 10px; font-family: 'Montserrat', sans-serif; font-size: 30px;"><%=recipe_name.get(i)%></div>
					<!--
				<div style="display: inline-block; position: relative;">
					<div class="icons-share">Share This:</div>
					<div class="icons">
						<a href="#"><i class="fa fa-facebook-square fa-2x"></i></a>
					</div>
					<div class="icons">
						<a href="#"><i class="fa fa-twitter-square fa-2x"
							style="color: #4099FF"></i></a>
					</div>
					<div class="icons">
						<a href="#"><i class="fa fa-google-plus-square fa-2x"
							style="color: #dd4b39"></i></a>
					</div>
					<div class="icons">
						<a href="#"><i class="fa fa-linkedin-square fa-2x"></i></a>
					</div>
					<div class="icons">
						<a href="#"><i class="fa fa-pinterest-square fa-2x"
							style="color: #C92228"></i></a>
					</div>


				</div>
				-->
				</div>

			</div>
			<div
				style="margin: auto; text-align: center; margin-bottom: 40px; width: 90%; display: inline-block; position: relative;">
				<%
					if (!recipe_video_url.get(i).equals("na")) {
				%>
				<iframe width="860" height="415" src="<%=recipe_video_url.get(i)%>"
					frameborder="0" allowfullscreen></iframe>
				<!--  <%=recipe_video_url.get(i)%> -->
				<%
					} else {
				%>

				<div class="videoNotAvailable">Video Not available</div>
				<%
					}
				%>
			</div>
			<div
				style="margin: auto; display: inline-block; position: relative; text-align: center; width: 100%">
				<div
					style="width: 100%; display: inline-block; position: relative; text-align: center !important; margin-left: 0%;">
					<div
						style="margin: auto; display: inline-block; position: relative; width: 90%; text-align: center !important;">
						<div class="procedure">
							<div style="font-size: 15px; font-weight: bold;">COOKING
								PROCEDURE:</div>
							<hr style="border-top: 3px solid #ff6600;" />

							<div style="text-align: justify;"><%=recipe_instruction.get(i)%></div>






							<%
								ArrayList<ArrayList<String>> ing_info_set = ingredient_details
												.get(i);
										ArrayList<String> ingNameList = ing_info_set.get(0);
										ArrayList<String> ingImageURLList = ing_info_set.get(1);
										ArrayList<String> ingAmountList = ing_info_set.get(2);
										ArrayList<String> ingUnitList = ing_info_set.get(3);
										ArrayList<String> ingDiscripList = ing_info_set.get(4);
										ArrayList<String> blurInfo = ing_info_set.get(5);
										HashMap<String, String> replace = rep_info.get(i);
							%>
							<%
                            if (!recipe_note.get(i).equals("na")) {
                        %>

                        <p>
                            <br>
                        <div class="notes">
                            <%=recipe_note.get(i)%>
                        </div>
                        </p>
                        <%
                            }
                        %>
						</div>

						<div class="procedure2">

							<div style="font-size: 15px; font-weight: bold;">INGREDIENTS</div>
							<hr style="border-top: 3px solid #ff6600;" />
							<div style="display: inline-block; position: relative;">
								<%
									for (int j = 0; j < ingNameList.size(); j++) {
								%>




								<div
									style="display: inline-block; position: relative; width: 100%;">
									<div
										style="float: left; left: 0px; width: 50%; margin-top: 20px; display: inline-block; position: relative; padding-right: 10px;"><%=ingDiscripList.get(j)%></div>
									<div style="display: inline-block; position: relative;">
										<div style="display: inline-block; position: relative;">
											<%
												if (blurInfo.get(j).equals("1")) {
											%>
											<img src="<%=ingImageURLList.get(j)%>" alt="" height="50"
												width="50" style="border-radius: 50px" class="nomal" />
											<%
												} else {
											%>

											<img src="<%=ingImageURLList.get(j)%>" alt="" height="50"
												width="50" style="border-radius: 50px" class="blur" />
											<%
												}
											%>
										</div>
										<div style="display: inline-block; position: relative;">
											<%
												if (replace.containsKey(ingImageURLList.get(j))) {
																String x = replace.get(ingImageURLList.get(j));
											%>

											<img src="<%=x%>" height="50" width="50"
												style="border-radius: 50px" />


											<%
												}
											%>
										</div>

									</div>
								</div>
								<%
									}
								%>

							</div>
						</div>
					</div>
				</div>
			</div>
			<br />


		</div>
		<%
		}
	%>
	</div>
	
	<div style="position: relative; display: inline-block; top: 0; margin-top: 60px;">
		<img src="images_video/advert1.png" /><br /> <br /> <img
			src="images_video/banner2.jpg" />
		</div>
	</div>



	
	<%
		}
	%>
	<%
		if (st == 0) {
	%>


	<div
		style="margin: auto; width: 50%;  text-align: center; padding: 20px;">
		<img src="images_video/oops.jpg" /><img>
	</div>


	<%
		}
	%>


</body>
</html>
