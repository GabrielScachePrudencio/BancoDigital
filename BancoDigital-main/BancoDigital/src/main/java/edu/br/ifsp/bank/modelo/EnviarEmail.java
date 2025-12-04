	package edu.br.ifsp.bank.modelo;
	
	import java.util.Properties;
	
	import jakarta.mail.Authenticator;
	import jakarta.mail.Message;
	import jakarta.mail.PasswordAuthentication;
	import jakarta.mail.Session;
	import jakarta.mail.Transport;
	import jakarta.mail.internet.InternetAddress;
	import jakarta.mail.internet.MimeMessage;
	
	public class EnviarEmail {
	
	    private static final String EMAIL_REMETENTE = "bancodigital133@gmail.com";
	
	    // NÃO APAGAR
	    private static final String SENHA = "wyjipifdlixzuosj";
	    
	
	    public static void enviarEmailComCodigo(String emailDestino) {
	
	        String codigo = GeradorCodigos.getInstance().getCodigoInt().toString();
	
	        String assunto = "Código de Autenticação";
	        String mensagem = "Seu código é: " + codigo + "\n"
	                + "Digite este código no sistema para validar seu acesso.";
	
	        try {
	
	            Properties props = new Properties();
	            props.put("mail.smtp.auth", "true");
	            props.put("mail.smtp.starttls.enable", "true");
	            props.put("mail.smtp.starttls.required", "true");
	            props.put("mail.smtp.host", "smtp.gmail.com");
	            props.put("mail.smtp.port", "587");
	
	            // PARA ANGUS FUNCIONAR PERFEITOzz
	            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	            
	            
	            Session session = Session.getInstance(props, new Authenticator() {
	                @Override
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(EMAIL_REMETENTE, SENHA);
	                }
	            });
	
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(EMAIL_REMETENTE));
	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
	            msg.setSubject(assunto);
	            msg.setText(mensagem);
	
	            Transport.send(msg);
	
	            System.out.println("E-mail enviado com sucesso! com codigo " + codigo);
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public static void EnviarEmailParaTrocarSenha(String emailDestino, int id, int codigo) {
	    	 
	
	         String assunto = "Trocar Senha";
	         String link = "http://localhost:8080/BancoDigital/pessoa/trocarsenha?codigo="+codigo+"&id="+id;
	         String mensagem = "Click aqui para trocar sua senha " + link;
	
	         try {
	
	             Properties props = new Properties();
	             props.put("mail.smtp.auth", "true");
	             props.put("mail.smtp.starttls.enable", "true");
	             props.put("mail.smtp.starttls.required", "true");
	             props.put("mail.smtp.host", "smtp.gmail.com");
	             props.put("mail.smtp.port", "587");
	
	             // PARA ANGUS FUNCIONAR PERFEITOzz
	             props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	
	             
	             
	             Session session = Session.getInstance(props, new Authenticator() {
	                 @Override
	                 protected PasswordAuthentication getPasswordAuthentication() {
	                     return new PasswordAuthentication(EMAIL_REMETENTE, SENHA);
	                 }
	             });
	
	             Message msg = new MimeMessage(session);
	             msg.setFrom(new InternetAddress(EMAIL_REMETENTE));
	             msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
	             msg.setSubject(assunto);
	             msg.setText(mensagem);
	
	             Transport.send(msg);
	
		            System.out.println("E-mail enviado com sucesso! para trocar senha com codigo " + codigo);
	
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	    }
	}
