package deal.bazaar.dealsbazzar.controllers;

import java.util.List;
import java.util.Objects;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import deal.bazaar.dealsbazzar.models.ResponseData;
//import com.alchemy.patient.model.Patient;
import deal.bazaar.dealsbazzar.models.SystemUser;
import deal.bazaar.dealsbazzar.repositories.SystemUserRepository;
import deal.bazaar.dealsbazzar.responses.JWTResponseData;
import deal.bazaar.dealsbazzar.responses.Response;
import deal.bazaar.dealsbazzar.responses.ResponsesData;
import deal.bazaar.dealsbazzar.security.JwtTokenUtil;
import deal.bazaar.dealsbazzar.services.SystemUserService;

@CrossOrigin
@RestController
@RequestMapping("/web")
public class WebController 
{

    @Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private SystemUserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
    @Autowired
    PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private SystemUserRepository systemUserRepository;

	@PostMapping("/register")
	public ResponsesData saveUser(@RequestBody SystemUser user) 
	{
		
		List <SystemUser> userdata=systemUserRepository.findAll();
		if (systemUserRepository.count()>0){
			for(SystemUser userData:userdata){
				if (userData.getEmail().equals(user.getEmail())){
					return new ResponsesData("Email Already Exists",400,false);
			}
		}
	}
		SendVerifyMail(user.getName(),user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        systemUserRepository.save(user);
		
		return new ResponsesData("Saved Successfully",200,true);
	}
	
       
	/* if (newUser == null)
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	SystemUser newUser = userService.saveUser(user);
	if (newUser == null)
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	else
		return ResponseEntity.ok(newUser);
} */
	private boolean SendVerifyMail(String name,String email) 
	{
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
	        messageHelper.setFrom("srikarkalle007k@gmail.com");
	        messageHelper.setTo(email);
	        messageHelper.setSubject("Verification Mail from DealsBazzar");
	        messageHelper.setText("<b><a href=https://dealsbazzar.herokuapp.com/web/vrifcation/"+email+">click </a><h1>"+email+"</h1></b>", true);
	        javaMailSender.send(mimeMessage);
			return true;
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

	@GetMapping("/vrifcation/{email}")
    public String vrification(@PathVariable String email)
    {
		SystemUser userdata = systemUserRepository.findByEmail(email);
        //SystemUser userdata=systemUserRepository.findById(userId).get();
        userdata.setIsActive(true);
        systemUserRepository.save(userdata);
        return "<h1>"+email+"</h1>";
    }

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody SystemUser user) 
	{
		try {
			SystemUser userdata=systemUserRepository.findByEmail(user.getEmail());
            if(userdata.getIsActive()){
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

			SystemUser newUser = userService.getByEmail(user.getEmail());
			final String token = jwtTokenUtil.generateToken(newUser);

			return ResponseEntity.ok(new JWTResponseData(true, token, "Login Successfully"));
            }else{
                return ResponseEntity.ok(new JWTResponseData(false, "", "verify Email!!"));
            }

			/* return ResponseEntity.ok(new JWTResponseData(true, token, "Login Successfully")); */
		} catch (DisabledException e) {
			return ResponseEntity.ok(new JWTResponseData(false, "", "User Disabled !"));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new JWTResponseData(false, "", "Invalid User !"));
		}
	}

	@PostMapping("/logout")
    public ResponsesData log(@RequestBody String logutstring)
    {
        System.out.println(logutstring);
        return new ResponsesData("Logout Sucessfully",200,true);
    }
	
/* 	public ResponseEntity login(@RequestBody Hospitals hospitals) 
	{
		try {
            Hospitals hosdata=hospitalsRepository.findById(hospitals.getHospitalid()).get();
            System.out.println(hosdata);
            if(hosdata.isHosstatus()){
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(hospitals.getHospitalid(),hospitals.getPassword()));

           // boolean hosdataisthare=hospitalsRepository.existsById(hospitals.getHospitalid());
            
            String hosdatais=hospitalsDetailService.gethosid(hospitals);

			final String token = jwtTokenUtil.generateToken(hosdatais);
            
			return ResponseEntity.ok(new JWTResponseData(true, token, "Login Successfully",hosdatais));
            }else{
                return ResponseEntity.ok(new JWTResponseData(false, "", "verify Email!!",""));
            }
		} catch (DisabledException e) {
			return ResponseEntity.ok(new JWTResponseData(false, "", "User Disabled !",""));
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok(new JWTResponseData(false, "", "Invalid User !",""));
		} catch (Exception e){
            return ResponseEntity.ok(new JWTResponseData(false, "", "Invalid User !",""));
        }
	} */
    


}