


/**
 *
 * @author Pranay Rajesh Gheewala(B00826923)
 * Program that translates the contents of a text file into an HTML 1.0 web page and prints
 * that HTML to the screen.
 * 
 */

import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;

public class Javaassignment1 {

    public static void main(String[] args) throws FileNotFoundException, IOException, NullPointerException
    
    
    {
        
        System.out.println("Enter file name or file path: \n");   // Input filename from the user
        Scanner sc = new Scanner(System.in);   //Reading filename from the user
        String filename = sc.nextLine();   //storing filename in variable filename
        sc.close();
        File txtfile = new File(filename);  // open input stream filename for reading purpose.
        FileReader fr = new FileReader(txtfile);
        BufferedReader br = new BufferedReader(fr); 
        if(filename.contains(".txt"))  //check whether file is a .txt format
        {
            
        try{
            
        /* boolean flag array for checking the number of annotation encountered in paragraph
           flag[0] is used for checking *(bold) annotation
           flag[1] is used for checking _(italic) annotation
           flag[2] is used for checking %(underline) annotation*/
        Boolean flag[]={false,false,false};
        int paragraphOrList = 0; // initializing variable paragraphOrlist to 0 to keep count of paragraph or list tags 
        
        String line = br.readLine(); //reading line from text file into variable line
      
       
            while(line.length()==0) // to skip empty lines before any lines are encountered in the text file
            {
                line = br.readLine();
            }
            
             String htmltag = "<html>";  
        
             System.out.println(htmltag); //Printing html tag
        
      
            if(line.trim().substring(0,6).equals("title:")) // checking if the starting of the line contains title:
            {
                System.out.println("<head>\n<title>"+line.substring(7)+"</title>\n</head>"); //if yes then printing <head> <title> tag and the metadata after
                                                                                             //the title: annotation and closing </head> and </title> tag 
                line = br.readLine(); //reading nextline in the text file 
            }
      
            System.out.println("<body>\n"); //printing <body> tag
      
            while(line.length()==0) // to skip empty lines encountered 
            {
                line = br.readLine();
            }
      
            if(line.trim().charAt(0)=='-') //to check if the first character is '-'
            {
                int position = line.indexOf("-"); //to get position of the '-' annotation and storing position variable
                System.out.println("<ul>\n<li>"+line.substring(position+1)+"</li>"); //printing list opening tag and the string after the list annotaion
                                                                                     //and closing list tag
                                                                                     
                    while(line.trim().charAt(0)=='-' && (line = br.readLine())!=null && line.trim().length()>0)// to check more list items in the next lines
                    {
                        position = line.indexOf("-"); //to get position of the '-' annotation and storing position variable
                        System.out.println("<li>"+line.substring(position+1)+"</li>");//printing list opening tag and the string after the list annotaion
                                                                                     //and closing list tag
                    }
                paragraphOrList = 2; // set variable paragraphOrList to 2 to keep track of opened list tag 
            }
            else // if list not encountered than it will be paragraph
            {
                System.out.println("<p>"); //print opening paragraph tag
                paragraphOrList = 1; // set variable paragraphOrList to 1 to keep track of opened paragraph tag 
            }
     
            while(line!=null) //checking if lines are empty or not
            {  
                if(line.length()<=0) //checking till empty line is encountered 
                {
                    switch (paragraphOrList) //to check whether paragraphOrList tag is set to 1 or 2
                    {
                        case 1: //if variable paragraphOrList set to 1 
                        if(line.length()>0) //length of line is not null
                        {
                            
                            System.out.println(line); //print line 
                        
                        }
                    
                        if(flag[0]==true) // checking if bold flag is true
                        {
                            
                            flag[0]=false;    //set bold flag to false
                            System.out.println("</b>"); //print bold closing tag
      
                        }
                
                        if(flag[1]==true) // checking if italic flag is true
                        {
                            flag[1]=false; //set italic flag to false
                            System.out.println("</i>"); //print italic closing tag
      
                        }
                        
                        if(flag[2]==true) // checking if underline flag is true
                        {
                            flag[2]=false; //set underline flag to false
                            System.out.println("</u>"); //print underline closing tag
                        }
                    
                        System.out.println("</p>"); //print closing paragraph tag
                        paragraphOrList = 0; //set variable paragraphOrList to zero 
                        break;
                
                        case 2: //if variable paragraphOrList set to 2 
                        System.out.println("</ul>"); //print closing list tag
                        paragraphOrList = 0; //set variable paragraphOrList to zero
                        break;
                    }
            
                    
                    line = br.readLine();            
                
                }
        
                if(line==null) //slip blank lines
                {
                    continue;
                }
                if(line.trim().charAt(0) == '-') //checking if there are list items
                {
                       
                    System.out.println("<ul>\n<li>"+line.trim().substring(1)+"</li>");
                
                        while(line.trim().charAt(0)=='-' && (line = br.readLine())!=null && line.trim().length()>0)
                        {
                            System.out.println("<li>"+line.trim().substring(1)+"</li>");
                        }
                
                    System.out.println("</ul>");
                }
            
                else //checking if it is paragraph
                {
                       if(paragraphOrList==0)
                        {
                            
                            System.out.println("<p>");
                            paragraphOrList = 1;
                        }
                
                       while(line.length()<=0) //check till blank line is encountered
                        {
                            line = br.readLine();
                        }
                
                       String words [] = line.split(" "); //splitting the lines into the words and storing in array words
                
                       for(int i=0;i<words.length;i++) //iterating over every word encountered
                        {
                   
                            words[i] = words[i].trim(); //trimming the blank space at the beginning
                    
                            while(words[i].contains("*")) //checking if the word contains bold annotation
                            {
                                if(flag[0]==false) //check if the bold flag is false
                                {
                            
                                    flag[0] = true; //set bold flag to true
                                    words[i] = "<b>" + words[i].replaceFirst("\\*", ""); // storing opening bold tag and removing bold annotation
                                                                                         //and adding the string after it in word array 
                                }
                                
                                else  //check if the bold flag is true
                                {
                                    
                                    flag[0] = false; //set bold flag to false
                                    words[i] = words[i].replaceFirst("\\*", "") + "</b>"; // storing string and adding closing bold tag
                                                                                         // in word array 
                                }
                            }
                    
                            while(words[i].contains("_"))  //checking if the word contains italic annotation
                            {
                                
                                if(flag[1]==false) //check if the italic flag is false
                                {
                            
                                    flag[1] = true; //set italic flag to true
                                    words[i] = "<i>" + words[i].replaceFirst("_", "");// storing opening italic tag and removing italic annotation
                                                                                         //and adding the string after it in word array 
                            
                                }
                        
                                else //check if the italic flag is true
                                {
                           
                                    flag[1] = false; //set italic flag to false
                                    words[i] = words[i].replaceFirst("_", "") +"</i>"; // storing string and adding closing italic tag
                                                                                       // in word array
                                }
                            }
                    
                            while(words[i].contains("%"))  //checking if the word contains underline annotation
                            {
                                
                                if(!flag[2]) //check if the underline flag is false
                                {
                                    
                                    flag[2] = true; //set underline flag to true
                                    words[i] = "<u>" + words[i].replaceFirst("%", ""); // storing opening underline tag and removing underline annotation
                                                                                         //and adding the string after it in word array  
                                }
                                
                                else //check if the underline flag is true
                                {
                            
                                    flag[2] = false; //set underline flag to false
                                    words[i] = words[i].replaceFirst("%", "") + "</u>"; // storing string and adding closing underline tag
                                                                                       // in word array
                                }
                            }
                    
                            
                            if(words[i].contains("!")) // check whether it conatins bold annotation
                            {
                        
                                words[i] = "<b>" + words[i].replaceAll("!", "") + "</b> "; //storing opening and closing bold tag and replacing all 
                                                                                            //all bold annotation 
                            }
                        
                        }
                
                        line = ""; //set variable line to null
                
                        for(int i=0;i<words.length;i++) //iterating over word array
                        {
                    
                            line = line + " " + words[i]; //store elements of word array in line variable
                        }
                
                        System.out.println(line); //print line
                }
        
                    line = br.readLine(); //read nextline
            
            }
      
                    if (paragraphOrList==1) //for closing paragraph, bold, italic, underline tags
                    {
          
                        if(flag[0]==true)
                        {
              
                            flag[0]=false;    
                            System.out.println("</b>");
      
                        }
                    
                        if(flag[1]==true)
                        {
          
                            System.out.println("</i>");
      
                        }             
      
                        if(flag[2]==true)
                        {
          
                            System.out.println("</u>");
                        }
          
                
                        System.out.println("</p>");
                        paragraphOrList = 0;
            
         
                    }
      
                    
                    System.out.println("</body>\n</html>");
                    br.close();   //closing file 
                }
            catch(NullPointerException e ) //catching exception
            {
               System.out.println("File is empty or File not found");
            }
        }
        else //if file is not a .txt file
        {
            System.out.println("Invalid file format.Please enter text file. ");
        }    
    }
}
