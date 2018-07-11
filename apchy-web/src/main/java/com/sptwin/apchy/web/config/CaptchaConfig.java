package com.sptwin.apchy.web.config;

import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.SimpleTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.awt.*;

@Configuration
public class CaptchaConfig {
    @Bean("captchaService")
    public GenericManageableCaptchaService captchaService(){
        GenericManageableCaptchaService genericManageableCaptchaService =
                new GenericManageableCaptchaService(imageEngine(),300,2000);
        return genericManageableCaptchaService;
    }
    @Bean("imageEngine")
    public GenericCaptchaEngine imageEngine(){
        CaptchaFactory[] factories = {captchaFactory()};
        GenericCaptchaEngine genericCaptchaEngine = new GenericCaptchaEngine(factories);
        return genericCaptchaEngine;
    }
    @Bean("captchaFactory")
    public GimpyFactory captchaFactory(){
        GimpyFactory gimpyFactory = new GimpyFactory(wordgen(),wordtoimage());
        return gimpyFactory;
    }
    @Bean("wordgen")
    public RandomWordGenerator wordgen(){
        RandomWordGenerator randomWordGenerator = new RandomWordGenerator("0123456789");
        return randomWordGenerator;
    }
    @Bean("wordtoimage")
    public ComposedWordToImage wordtoimage(){
        ComposedWordToImage composedWordToImage =
                new ComposedWordToImage(fontGenRandom(),backGenUni(),simpleWhitePaster());
        return composedWordToImage;
    }
    @Bean("fontGenRandom")
    public RandomFontGenerator fontGenRandom(){
        RandomFontGenerator randomFontGenerator = new RandomFontGenerator(17,17);
        return randomFontGenerator;
    }
    @Bean("backGenUni")
    public GradientBackgroundGenerator backGenUni(){
        GradientBackgroundGenerator gradientBackgroundGenerator =
                new GradientBackgroundGenerator(50,25,colorGrey(),colorGreen());
        return gradientBackgroundGenerator;
    }
    @Bean("simpleWhitePaster")
    public SimpleTextPaster simpleWhitePaster(){
        SimpleTextPaster simpleTextPaster =
                new SimpleTextPaster(4,4,colorFont());
        return simpleTextPaster;
    }
    @Bean("colorGrey")
    public Color colorGrey(){
        Color color = new Color(200,255,200);
        return color;
    }
    @Bean("colorGreen")
    public Color colorGreen(){
        Color color = new Color(110,120,200);
        return color;
    }
    @Bean("colorFont")
    public Color colorFont(){
        Color color = new Color(60,60,60);
        return color;
    }
}
