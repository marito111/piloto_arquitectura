--CARGA LOS PERFILES
INSERT INTO USER_PROFILE(type) VALUES ('USER');
INSERT INTO USER_PROFILE(type) VALUES ('ADMIN');
INSERT INTO USER_PROFILE(type) VALUES ('DBA');
  
--CARGA LOS USUARIOS
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email) VALUES ('mario','$2a$10$VDEtWzJvCaUILejWNy7JUearNYzwjbZCM3FLqa4rtbSdC8lVnDLr.', 'MARIO','ADMIN','mario@xyz.com');
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email) VALUES ('pepe','$2a$10$Q85idmB4Qst7tR2sQdUavO6tB/92JmT5OKA67SdpALMFddrxjRoNi', 'pepe','USER','pepe@xyz.com');
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email) VALUES ('juan','$2a$10$mVHpvpKZF.V4eF5RUOuXI.XmRdBQurg.n2EyIgexB/C5hxnf3rMWO', 'juan','USER','juan@xyz.com');
INSERT INTO APP_USER(sso_id, password, first_name, last_name, email) VALUES ('luis','$2a$10$tnjZe1my5dbcwJukDcpJ3eEb7Pxxxbu8IMzq2ZgusHOzgRfEhGgQW', 'luis','USER','luis@xyz.com');

-- CARGA LA RELACCION  USUARIO - PERFIL
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id) SELECT user.id, profile.id FROM app_user user, user_profile profile where user.sso_id='mario' and profile.type='ADMIN';
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id) SELECT user.id, profile.id FROM app_user user, user_profile profile where user.sso_id='mario' and profile.type='USER';
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id) SELECT user.id, profile.id FROM app_user user, user_profile profile where user.sso_id='mario' and profile.type='DBA';
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id) SELECT user.id, profile.id FROM app_user user, user_profile profile where user.sso_id='pepe' and profile.type='USER';
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id) SELECT user.id, profile.id FROM app_user user, user_profile profile where user.sso_id='juan' and profile.type='USER';
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id) SELECT user.id, profile.id FROM app_user user, user_profile profile where user.sso_id='luis' and profile.type='USER';
  
