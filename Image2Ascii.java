import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
public class Image2Ascii
{
	static char[] mask = "@#&$%*o!;.".toCharArray();

	public static void main(String[] args) 
	{
		if(args.length<1){
			System.out.println("WARN: Please input an image.");
			return;
		}
		DrawAscii(args[0]);
	}

	public static void DrawAscii(String path){
	
		String ascii = "";

		try{
			File file = new File(path);

			BufferedImage bufferedImage = ImageIO.read(new FileInputStream(file));

			int width = bufferedImage.getWidth();

			int height = bufferedImage.getHeight();

			BufferedImage grayBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					//calculate the gray
					final int color = bufferedImage.getRGB(x, y);
					final int r = (color >> 16) & 0xff;
					final int g = (color >> 8) & 0xff;
					final int b = color & 0xff;
					int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
					int temp = gray*11/255;
					if (temp>=10)
					{
						ascii+=" ";
					}else{
						ascii+=mask[temp];
					}

					//System.out.println(""+r+","+g+","+b+"\r\n");
					int newPixel = 255;
					newPixel = newPixel <<8;
					newPixel += gray;
					newPixel = newPixel <<8;
					newPixel += gray;
					newPixel = newPixel <<8;
					newPixel += gray;

					grayBufferedImage.setRGB(x, y, newPixel);
				}
				ascii+="\r\n";

				System.out.println(ascii);
			}


			//ImageIO.write(grayBufferedImage, "JPEG", new File("out.jpg"));

		}catch(Exception e){

			System.out.println(e.toString());
		}
	}
}
