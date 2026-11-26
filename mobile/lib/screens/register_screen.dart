import 'package:flutter/material.dart';
import '../providers/auth_provider.dart';
import '../routes/app_routes.dart';
import '../widgets/nexbuddy_button.dart';
import '../widgets/nexbuddy_text_field.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _firstNameCtrl = TextEditingController();
  final _lastNameCtrl = TextEditingController();
  final _usernameCtrl = TextEditingController();
  final _emailCtrl = TextEditingController();
  final _passwordCtrl = TextEditingController();
  final _confirmCtrl = TextEditingController();

  String? _error;
  bool _loading = false;

  @override
  void dispose() {
    _firstNameCtrl.dispose();
    _lastNameCtrl.dispose();
    _usernameCtrl.dispose();
    _emailCtrl.dispose();
    _passwordCtrl.dispose();
    _confirmCtrl.dispose();
    super.dispose();
  }

  Future<void> _submit() async {
    setState(() {
      _error = null;
      _loading = true;
    });
    final auth = AuthProviderScope.of(context);
    try {
      final res = await auth.authService.register(
        firstName: _firstNameCtrl.text.trim(),
        lastName: _lastNameCtrl.text.trim(),
        username: _usernameCtrl.text.trim(),
        email: _emailCtrl.text.trim(),
        password: _passwordCtrl.text,
        confirmPassword: _confirmCtrl.text,
      );
      final devOtp = (res['data'] as Map<String, dynamic>?)?['devOtp']?.toString();
      if (!mounted) return;
      Navigator.pushReplacementNamed(
        context,
        AppRoutes.verifyEmail,
        arguments: {'email': _emailCtrl.text.trim(), 'devOtp': devOtp},
      );
    } catch (e) {
      setState(() => _error = e.toString());
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Create Account')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            if (_error != null)
              Container(
                padding: const EdgeInsets.all(12),
                margin: const EdgeInsets.only(bottom: 16),
                decoration: BoxDecoration(
                  color: Colors.red.shade50,
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Text(_error!, style: TextStyle(color: Colors.red.shade800)),
              ),
            NexBuddyTextField(controller: _firstNameCtrl, label: 'First Name'),
            const SizedBox(height: 12),
            NexBuddyTextField(controller: _lastNameCtrl, label: 'Last Name'),
            const SizedBox(height: 12),
            NexBuddyTextField(controller: _usernameCtrl, label: 'Username'),
            const SizedBox(height: 12),
            NexBuddyTextField(
              controller: _emailCtrl,
              label: 'Email',
              keyboardType: TextInputType.emailAddress,
            ),
            const SizedBox(height: 12),
            NexBuddyTextField(controller: _passwordCtrl, label: 'Password', obscureText: true),
            const SizedBox(height: 12),
            NexBuddyTextField(controller: _confirmCtrl, label: 'Confirm Password', obscureText: true),
            const SizedBox(height: 20),
            NexBuddyButton(label: 'Register', onPressed: _submit, loading: _loading),
          ],
        ),
      ),
    );
  }
}
