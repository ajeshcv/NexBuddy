import 'package:flutter/material.dart';
import '../providers/auth_provider.dart';
import '../routes/app_routes.dart';
import '../widgets/nexbuddy_button.dart';
import '../widgets/nexbuddy_text_field.dart';

class VerifyEmailScreen extends StatefulWidget {
  final String? initialEmail;
  final String? initialDevOtp;

  const VerifyEmailScreen({super.key, this.initialEmail, this.initialDevOtp});

  @override
  State<VerifyEmailScreen> createState() => _VerifyEmailScreenState();
}

class _VerifyEmailScreenState extends State<VerifyEmailScreen> {
  late final TextEditingController _emailCtrl =
      TextEditingController(text: widget.initialEmail ?? '');
  late final TextEditingController _otpCtrl =
      TextEditingController(text: widget.initialDevOtp ?? '');
  late String? _message = widget.initialDevOtp != null && widget.initialDevOtp!.isNotEmpty
      ? 'Dev mode (SMTP disabled): Your OTP is ${widget.initialDevOtp}'
      : null;
  bool _isError = false;
  bool _loading = false;

  @override
  void dispose() {
    _emailCtrl.dispose();
    _otpCtrl.dispose();
    super.dispose();
  }

  Future<void> _verify() async {
    setState(() {
      _loading = true;
      _message = null;
      _isError = false;
    });
    final auth = AuthProviderScope.of(context);
    try {
      await auth.authService.verifyEmail(
        email: _emailCtrl.text.trim(),
        otp: _otpCtrl.text.trim(),
      );
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Email verified! Please sign in.')),
      );
      Navigator.pushNamedAndRemoveUntil(context, AppRoutes.login, (route) => false);
    } catch (e) {
      setState(() {
        _isError = true;
        _message = e.toString();
      });
    } finally {
      if (mounted) setState(() => _loading = false);
    }
  }

  Future<void> _resend() async {
    final auth = AuthProviderScope.of(context);
    try {
      final res = await auth.authService.resendOtp(email: _emailCtrl.text.trim());
      final devOtp = (res['data'] as Map<String, dynamic>?)?['devOtp']?.toString();
      setState(() {
        _isError = false;
        if (devOtp != null && devOtp.isNotEmpty) {
          _otpCtrl.text = devOtp;
          _message = 'Dev mode: New OTP is $devOtp';
        } else {
          _message = res['message']?.toString() ?? 'OTP sent.';
        }
      });
    } catch (e) {
      setState(() {
        _isError = true;
        _message = e.toString();
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Verify Email')),
      body: Padding(
        padding: const EdgeInsets.all(24),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text(
              'Enter the 6-digit OTP sent to your email.',
              style: TextStyle(color: Colors.black54),
            ),
            const SizedBox(height: 16),
            if (_message != null)
              Container(
                padding: const EdgeInsets.all(12),
                margin: const EdgeInsets.only(bottom: 16),
                decoration: BoxDecoration(
                  color: _isError ? Colors.red.shade50 : Colors.green.shade50,
                  borderRadius: BorderRadius.circular(12),
                ),
                child: Text(
                  _message!,
                  style: TextStyle(color: _isError ? Colors.red.shade800 : Colors.green.shade800),
                ),
              ),
            NexBuddyTextField(controller: _emailCtrl, label: 'Email'),
            const SizedBox(height: 12),
            NexBuddyTextField(
              controller: _otpCtrl,
              label: '6-digit OTP',
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 20),
            NexBuddyButton(label: 'Verify Email', onPressed: _verify, loading: _loading),
            const SizedBox(height: 12),
            OutlinedButton(onPressed: _resend, child: const Text('Resend OTP')),
          ],
        ),
      ),
    );
  }
}
