import 'package:flutter/material.dart';
import '../providers/auth_provider.dart';
import '../routes/app_routes.dart';

class SettingsScreen extends StatelessWidget {
  const SettingsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final auth = AuthProviderScope.of(context);
    final settings = auth.profile?.settings;

    return Scaffold(
      appBar: AppBar(title: const Text('Settings')),
      body: ListView(
        padding: const EdgeInsets.all(16),
        children: [
          SwitchListTile(
            title: const Text('Enable Notifications'),
            value: settings?.notificationEnabled ?? true,
            onChanged: (val) => auth.updateSettings({'notificationEnabled': val}),
          ),
          SwitchListTile(
            title: const Text('Public Profile Visibility'),
            value: settings?.profileVisible ?? true,
            onChanged: (val) => auth.updateSettings({'profileVisible': val}),
          ),
          SwitchListTile(
            title: const Text('Location Visibility'),
            value: settings?.locationVisible ?? true,
            onChanged: (val) => auth.updateSettings({'locationVisible': val}),
          ),
          const Divider(height: 32),
          ListTile(
            leading: const Icon(Icons.logout, color: Colors.red),
            title: const Text('Logout', style: TextStyle(color: Colors.red, fontWeight: FontWeight.w600)),
            onTap: () async {
              await auth.logout();
              if (!context.mounted) return;
              Navigator.pushNamedAndRemoveUntil(context, AppRoutes.login, (route) => false);
            },
          ),
        ],
      ),
    );
  }
}
