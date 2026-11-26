import 'package:flutter/foundation.dart';

class AppConstants {
  static const String appName = 'NexBuddy';
  static const String tagline = 'One Platform. Endless Connections.';

  static String get apiBaseUrl {
    const defined = String.fromEnvironment('API_BASE_URL');
    if (defined.isNotEmpty) return defined;
    if (!kIsWeb && defaultTargetPlatform == TargetPlatform.android) {
      return 'http://10.0.2.2:8080/api/v1';
    }
    return 'http://localhost:8080/api/v1';
  }
}
