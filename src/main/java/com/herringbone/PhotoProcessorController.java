package com.herringbone;

import com.fasterxml.jackson.databind.ObjectMapper;
import marvin.image.MarvinImage;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;
import org.marvinproject.image.color.grayScale.GrayScale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/photo")
public class PhotoProcessorController {
    private static final String GAUSSIAN_BLUR = "gaussianBlur";
    private static final String CHARCOAL = "charcoal";

    @Autowired
    TickerRepository tickerRepository;

    @RequestMapping(value="/process", method=RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] processPhoto(@RequestPart("instructions") String instructions, @RequestPart("file") MultipartFile multipartFile ) throws Exception
    {
        Map jsonInstructions = new ObjectMapper().readValue(instructions, Map.class);

        ByteArrayInputStream bais = new ByteArrayInputStream(multipartFile.getBytes());
        BufferedImage bufferedImage = ImageIO.read(bais);
        MarvinImage image = new MarvinImage(bufferedImage);
        if (jsonInstructions.containsKey(CHARCOAL) && jsonInstructions.get(CHARCOAL).equals("true")) {
            GrayScale bw = new GrayScale();
            bw.load();
            bw.process(image, image);
        }
        if (jsonInstructions.containsKey(GAUSSIAN_BLUR) && jsonInstructions.get(GAUSSIAN_BLUR).equals("true")) {
            GaussianBlur gb = new GaussianBlur();
            gb.load();
            gb.setAttribute("radius", 15);
            gb.process(image.clone(), image);
        }
        image.update();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image.getBufferedImage(), "jpg", baos);

        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    @RequestMapping(value="/ticker/{symbol}", method = RequestMethod.GET)
    public @ResponseBody List<Ticker> test( @PathVariable String symbol) {
        List<Ticker> tickers = tickerRepository.findBySymbol(symbol);
//        List<Ticker> tickers = tickerRepository.findBySymbolOrAlias(symbol, symbol);
        return tickers;
    }
}
