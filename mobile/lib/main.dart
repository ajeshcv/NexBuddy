import 'package:flutter/material.dart';
import 'providers/auth_provider.dart';
import 'routes/app_routes.dart';
import 'utils/app_constants.dart';
import 'utils/app_theme.dart';

void main() {
  runApp(NexBuddyApp());
}

class NexBuddyApp extends StatelessWidget {
  NexBuddyApp({super.key});

  final AuthProvider _authProvider = AuthProvider();

  @override
  Widget build(BuildContext context) {
    return AuthProviderScope(
      provider: _authProvider,
      child: MaterialApp(
        title: AppConstants.appName,
        debugShowCheckedModeBanner: false,
        theme: AppTheme.lightTheme,
        initialRoute: AppRoutes.splash,
        onGenerateRoute: AppRoutes.onGenerateRoute,
      ),
    );
  }
}
