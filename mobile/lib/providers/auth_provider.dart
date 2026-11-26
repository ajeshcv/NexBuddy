import 'package:flutter/material.dart';
import '../models/interest_model.dart';
import '../models/user_model.dart';
import '../services/api_service.dart';
import '../services/auth_service.dart';
import '../services/user_service.dart';

class AuthProvider extends ChangeNotifier {
  final ApiService apiService = ApiService();
  late final AuthService authService = AuthService(apiService);
  late final UserService userService = UserService(apiService);

  UserProfileModel? profile;
  List<InterestModel> availableInterests = [];
  bool isLoading = false;

  bool get isAuthenticated => apiService.accessToken != null && profile != null;

  Future<void> login(String email, String password) async {
    isLoading = true;
    notifyListeners();
    try {
      final res = await authService.login(email: email, password: password);
      final data = res['data'] as Map<String, dynamic>;
      apiService.accessToken = data['token']?.toString();
      apiService.refreshToken = data['refreshToken']?.toString();
      await loadProfileAndInterests();
    } finally {
      isLoading = false;
      notifyListeners();
    }
  }

  Future<void> loadProfileAndInterests() async {
    profile = await userService.getMyProfile();
    availableInterests = await userService.getAvailableInterests();
    notifyListeners();
  }

  Future<void> updateProfile(Map<String, dynamic> data) async {
    profile = await userService.updateProfile(data);
    notifyListeners();
  }

  Future<void> updateInterests(List<String> interestIds) async {
    await userService.updateInterests(interestIds);
    profile = await userService.getMyProfile();
    notifyListeners();
  }

  Future<void> updateSettings(Map<String, dynamic> data) async {
    await userService.updateSettings(data);
    profile = await userService.getMyProfile();
    notifyListeners();
  }

  Future<void> logout() async {
    try {
      if (apiService.accessToken != null) {
        await authService.logout();
      }
    } catch (_) {
      // Clear local tokens regardless
    } finally {
      apiService.accessToken = null;
      apiService.refreshToken = null;
      profile = null;
      notifyListeners();
    }
  }
}

class AuthProviderScope extends InheritedNotifier<AuthProvider> {
  const AuthProviderScope({
    super.key,
    required AuthProvider provider,
    required super.child,
  }) : super(notifier: provider);

  static AuthProvider of(BuildContext context) {
    final scope = context.dependOnInheritedWidgetOfExactType<AuthProviderScope>();
    assert(scope != null, 'AuthProviderScope not found in widget tree');
    return scope!.notifier!;
  }
}
