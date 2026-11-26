import 'interest_model.dart';

class UserSettingsModel {
  final bool notificationEnabled;
  final bool profileVisible;
  final bool locationVisible;
  final String theme;
  final String language;

  UserSettingsModel({
    required this.notificationEnabled,
    required this.profileVisible,
    required this.locationVisible,
    required this.theme,
    required this.language,
  });

  factory UserSettingsModel.fromJson(Map<String, dynamic> json) {
    return UserSettingsModel(
      notificationEnabled: json['notificationEnabled'] == true,
      profileVisible: json['profileVisible'] != false,
      locationVisible: json['locationVisible'] != false,
      theme: json['theme']?.toString() ?? 'Light',
      language: json['language']?.toString() ?? 'English',
    );
  }

  Map<String, dynamic> toJson() => {
        'notificationEnabled': notificationEnabled,
        'profileVisible': profileVisible,
        'locationVisible': locationVisible,
        'theme': theme,
        'language': language,
      };
}

class UserProfileModel {
  final String id;
  final String firstName;
  final String lastName;
  final String username;
  final String? email;
  final String? bio;
  final String? city;
  final String? country;
  final String? occupation;
  final String? profilePicture;
  final List<InterestModel> interests;
  final UserSettingsModel? settings;

  UserProfileModel({
    required this.id,
    required this.firstName,
    required this.lastName,
    required this.username,
    this.email,
    this.bio,
    this.city,
    this.country,
    this.occupation,
    this.profilePicture,
    this.interests = const [],
    this.settings,
  });

  String get fullName => '$firstName $lastName'.trim();

  factory UserProfileModel.fromJson(Map<String, dynamic> json) {
    final rawInterests = json['interests'] as List<dynamic>? ?? [];
    return UserProfileModel(
      id: json['id']?.toString() ?? '',
      firstName: json['firstName']?.toString() ?? '',
      lastName: json['lastName']?.toString() ?? '',
      username: json['username']?.toString() ?? '',
      email: json['email']?.toString(),
      bio: json['bio']?.toString(),
      city: json['city']?.toString(),
      country: json['country']?.toString(),
      occupation: json['occupation']?.toString(),
      profilePicture: json['profilePicture']?.toString(),
      interests: rawInterests
          .whereType<Map<String, dynamic>>()
          .map(InterestModel.fromJson)
          .toList(),
      settings: json['settings'] is Map<String, dynamic>
          ? UserSettingsModel.fromJson(json['settings'] as Map<String, dynamic>)
          : null,
    );
  }
}
