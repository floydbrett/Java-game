package engine.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class Animation {
	public static final String RES_PATH = "res/";

	private Animation tempOverridingAnimation = null;
	private String path;
	private ArrayList<BufferedImage> frames = new ArrayList<>();
	private int index = 0;
	private int tick = 0, maxTick = 1;

	public Animation(String path) {
		this.path = RES_PATH + path + "/";
		loadFrames();
	}

	public Animation(String path, int maxTick) {
		this(path);
		this.maxTick = maxTick;
	}

	private void loadFrames() {
		ArrayList<File> files = new ArrayList<>(List.of(Objects.requireNonNull(new File(path).listFiles())));
		Collections.sort(files, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return extractInt(o1.getName()) - extractInt(o2.getName());
			}

			int extractInt(String s) {
				String num = s.replaceAll("\\D", "");
				return num.isEmpty() ? 0 : Integer.parseInt(num);
			}
		});
		for(File file : files) {
			String fileName = file.getName();
			String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(extension.equals("png")) {
				try {
					frames.add(ImageIO.read(file));
					System.out.println(fileName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public BufferedImage getFrame() {
		BufferedImage out = null;
		if(tempOverridingAnimation != null) {
			out = tempOverridingAnimation.getFrame();
			if(tempOverridingAnimation.index >= tempOverridingAnimation.frames.size() - 1) {
				//System.out.println(tempOverridingAnimation.index+"/"+tempOverridingAnimation.frames.size());
				tempOverridingAnimation = null;
			}
			return out;
		} else if(frames.size() != 0) {
			out = frames.get(index);
			tick = (tick + 1) % maxTick;
			if(tick == maxTick - 1) {
				index = (index + 1) % frames.size();
			}
		}
		return out;
	}

	public void setTempOverridingAnimation(Animation tempOverridingAnimation) {
		this.tempOverridingAnimation = tempOverridingAnimation;
	}

	public boolean hasTempOverridingAnimation() {
		return tempOverridingAnimation != null;
	}
}
