import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Map;

class Artist{
    String name;
    String hometown;
    String bio;
    String photoURL;

    public Artist(String name, String hometown, String bio, String photoURL){
        this.name = name;
        this.hometown = hometown;
        this.bio = bio;
        this.photoURL = photoURL;
    }
}

public class artist_scrapping {

    public static void main(String[] args) {

        String url = "https://www.thefader.com/2016/02/23/global-artists-you-need-to-know";

        List<Artist> artistList = new ArrayList<>();

        try{
            Document document = Jsoup.connect(url).get();
            Elements artists = document.select(".headline");
            Elements hometowns = document.select(".paragraph_wrapper.center_align");
            Elements stories = document.select(".media_wrapper");

            for(int i = 0; i< artists.size(); i++){
                Element headline = artists.get(i);
                String name = headline.select("h5").text();

                String actualName;
                if (name.charAt(1) == '.'){
                    actualName = name.substring(3);
                    System.out.println("Artist name: " + " - " + actualName);
                }
                else{
                    actualName = name.substring(4);
                    System.out.println("Artist name: " + " - " + actualName);
                }

                Element hometown = hometowns.get(i);
                String town = hometown.select("p").text();
                String actualHometown = "";

                if (!town.isEmpty() && town.length() <= 50) {
                    actualHometown = town.charAt(2) == ' ' ? town.substring(3) : town.substring(5);
                    System.out.println("Hometown: " + actualHometown);
                }

                Element story = stories.get(i);
                String bio = story.select("p").text();
                Element imgElement = story.selectFirst("img");
                String imgSRC = imgElement.attr("src");

                System.out.println("Bio: " + bio);
                System.out.println("Image URL: " + imgSRC + "\n\n");

                artistList.add(new Artist(actualName, actualHometown, bio, imgSRC));

            }

        }catch(IOException e){
            e.printStackTrace();
        }

    }
}


