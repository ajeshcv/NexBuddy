import 'api_service.dart';

class AuthService {
  final ApiService api;

  AuthService(this.api);

  Future<Map<String, dynamic>> health() => api.get('/health');

  Future<Map<String, dynamic>> register({
    required String firstName,
    required String lastName,
    required String username,
    required String email,
    required String password,
    required String confirmPassword,
  }) {
    return api.post('/auth/register', body: {
      'firstName': firstName,
      'lastName': lastName,
      'username': username,
      'email': email,
      'password': password,
      'confirmPassword': confirmPassword,
    });
  }

  Future<Map<String, dynamic>> verifyEmail({required String email, required String otp}) {
    return api.post('/auth/verify-email', body: {'email': email, 'otp': otp});
  }

  Future<Map<String, dynamic>> resendOtp({required String email}) {
    return api.post('/auth/resend-otp', body: {'email': email});
  }

  Future<Map<String, dynamic>> login({required String email, required String password}) {
    return api.post('/auth/login', body: {'email': email, 'password': password});
  }

  Future<void> logout() async {
    await api.post('/auth/logout');
  }
}
