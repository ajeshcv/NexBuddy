import '../models/interest_model.dart';
import '../models/user_model.dart';
import 'api_service.dart';

class UserService {
  final ApiService api;

  UserService(this.api);

  Future<UserProfileModel> getMyProfile() async {
    final res = await api.get('/users/me');
    return UserProfileModel.fromJson(res['data'] as Map<String, dynamic>);
  }

  Future<UserProfileModel> updateProfile(Map<String, dynamic> data) async {
    final res = await api.put('/users/profile', body: data);
    return UserProfileModel.fromJson(res['data'] as Map<String, dynamic>);
  }

  Future<List<InterestModel>> getAvailableInterests() async {
    final res = await api.get('/interests');
    final items = res['data'] as List<dynamic>? ?? [];
    return items
        .whereType<Map<String, dynamic>>()
        .map(InterestModel.fromJson)
        .toList();
  }

  Future<void> updateInterests(List<String> interestIds) async {
    await api.put('/users/interests', body: {'interestIds': interestIds});
  }

  Future<UserSettingsModel> updateSettings(Map<String, dynamic> data) async {
    final res = await api.put('/users/settings', body: data);
    return UserSettingsModel.fromJson(res['data'] as Map<String, dynamic>);
  }
}
