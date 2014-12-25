
import java.io.*;
import java.net.*;

///////////Code that downloads images referred to in a URL///////////

public class GetImage {

	public static GetImageGUI g;
	public String url;
	public String directory;

	public GetImage(GetImageGUI gui) {
		g = gui;
	}

	public void setUrl(String link) {
		url = link;
	}

	public void setDirectory(String dir) {
		directory = dir;
	}

	public void run() throws Exception {	
		try {

			//Parse the URL into various parts: protocol, host, port, filename.
			//First check the URL String
			if(!url.substring(7).contains("/")) {
				url = url.concat("/");
			}
			int end = url.substring(7, url.length()).indexOf("/");
			String host = url.substring(7, end+7), filename;

			if(url.length()>end+9)
				filename = url.substring(end+7, url.length());
			else
				filename = "/";

			//Establish connection
			Socket socket = new Socket(host, 80);

			//Get input and output streams for the socket.
			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder sb = new StringBuilder();
			PrintWriter wrServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			//Send the HTTP GET command to the Web server, specifying the file.			
			wrServer.println("GET " + filename + " HTTP/1.1");
			wrServer.println("Host: " + host);
			//Followed by newline.
			wrServer.println(""); 
			//Send it to the output steam which is connected to the server.
			wrServer.flush();  

			//Now read the server's response, and write it to the file.
			byte[] buffer = new byte[4096];
			int bytesRead, j, count = 1;
			String[] splitted, temp;
			PrintWriter wrImage;
			Socket imageSocket;
			String line, imageUrl, imageHost, imageFilename; 
			InputStream rdImage;
			//Get an output stream that accepts output bytes and sends them to some sink.
			FileOutputStream fos;

			//Read the html reply.
			while ((line = rd.readLine()) != null)
			{		
				sb.append(line + '\n');
			}	          

			//Check if the html code contains any images of the required format.
			if(!sb.toString().toLowerCase().contains("img src=\"")) {
				g.printInfo("The html received from the server does not contain any image tags.");
			}

			else {
				//Parse the html file to retrieve the img links.
				//Splitted[i] contains the String of the URL for an image.
				splitted = sb.toString().replaceAll("IMG SRC=\"", "img src=\"").split("img src=\"");

				for(int i=1; i<splitted.length; i++) {
					//Temp contains the String of the URL of an image and other code that comes after the img tag.
					temp = splitted[i].split("\"");

					//Check if it contains on of the popular image formats. If it does not, the URL
					//may be a link to elsewhere.
					if(temp[0].contains("gif") || temp[0].contains("png") || temp[0].contains("jpg") || temp[0].contains("jpeg")) { 

						imageUrl = temp[0];

						//Depending on the format of the data, we will need to parse it accordingly
						if(temp[0].contains("https")) {
							//Parse the URL into various parts: protocol, host, port, filename.
							end = imageUrl.substring(8, imageUrl.length()).indexOf("/");
							imageHost = imageUrl.substring(8, end+8);
							imageFilename = imageUrl.substring(end+8, imageUrl.length());
							imageSocket = new Socket(imageHost, 80);
						}

						else if(temp[0].contains("http")) {
							//Parse the URL into various parts: protocol, host, port, filename.
							end = imageUrl.substring(7, imageUrl.length()).indexOf("/");
							imageHost = imageUrl.substring(7, end+7);
							imageFilename = imageUrl.substring(end+7, imageUrl.length());
							imageSocket = new Socket(imageHost, 80);
						}

						//This image link is of a relative path. The parent directory is 'absent' in the string
						//and hence we will need to append it ourselves
						else {
							//Parse the URL into various parts: protocol, host, port, filename.
							end = imageUrl.substring(imageUrl.length()).indexOf("/");
							imageHost = host;
							imageFilename = "/" + imageUrl;
							imageUrl = "http://" + imageHost + imageFilename;
							imageSocket = new Socket(imageHost, 80);
						}

						//Get input and output streams for the socket connected to the image host.
						wrImage = new PrintWriter(new OutputStreamWriter(imageSocket.getOutputStream()));
						rdImage = imageSocket.getInputStream();
						fos = new FileOutputStream(directory + "\\" + "Image " + count + ".jpeg");

						// Send the HTTP GET command to the Web server, specifying the file.
						wrImage.println("GET " + imageUrl + " HTTP/1.0");
						wrImage.println(""); // Followed by newline
						wrImage.flush();

						//Read the reply, which includes the image data and a header.
						//Then the amount of bytes is stored as an int.
						bytesRead = rdImage.read(buffer);
						//If no bytes read, we can skip.
						if(bytesRead > 0) {
							//This loops reads the header.
							for (j=0; j < bytesRead; j++) {
								System.out.write(buffer[j]);

								if ((j >= 4) && 
										(buffer[j-3] == '\r') && (buffer[j-2] == '\n') &&
										(buffer[j-1] == '\r') && (buffer[j] == '\n')) {
									//Break after the header is over.
									break;
								}
							}
							j++;
							//Now copy rest of first buffer to file.

							fos.write(buffer, j, bytesRead - j);
							//Send download acknowledgement to the user of the GUI.
							g.printInfo("Image " + count + " downloaded.");
							//Now read rest of socket and copy to file.
							while((bytesRead = rdImage.read(buffer)) != -1)
								fos.write(buffer, 0, bytesRead);
							count++;
						}

						//When the server closes the connection, we close our stuff
						wrImage.close();
						rdImage.close();
						imageSocket.close();
						fos.close();
						//Distinguish files
					}
				}

				//Reply to GUI after attempt complete
				if(count!=1)
					g.printInfo("Download complete!");

				else
					g.printInfo("No images were downloaded!");

				//When the server closes the connection, we close our stuff
				rd.close();
				wrServer.close();
				socket.close();
				sb = null;
			}
		}
		//Handling of exceptions
		catch (Exception e) {
			g.printDirect("Error!");
			System.err.println(e);
		}
	}
}