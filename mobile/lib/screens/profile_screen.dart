import 'package:flutter/material.dart';
import '../providers/auth_provider.dart';
import '../widgets/nexbuddy_button.dart';
import '../widgets/nexbuddy_text_field.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  final _firstNameCtrl = TextEditingController();
  final _lastNameCtrl = TextEditingController();
  final _cityCtrl = TextEditingController();
  final _occupationCtrl = TextEditingController();
  final _bioCtrl = TextEditingController();
  Set<String> _selectedInterestIds = {};
  bool _initialized = false;
  bool _saving = false;

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    if (_initialized) return;
    final profile = AuthProviderScope.of(context).profile;
    if (profile != null) {
      _firstNameCtrl.text = profile.firstName;
      _lastNameCtrl.text = profile.lastName;
      _cityCtrl.text = profile.city ?? '';
      _occupationCtrl.text = profile.occupation ?? '';
      _bioCtrl.text = profile.bio ?? '';
      _selectedInterestIds = profile.interests.map((i) => i.id).toSet();
      _initialized = true;
    }
  }

  @override
  void dispose() {
    _firstNameCtrl.dispose();
    _lastNameCtrl.dispose();
    _cityCtrl.dispose();
    _occupationCtrl.dispose();
    _bioCtrl.dispose();
    super.dispose();
  }

  Future<void> _save() async {
    setState(() => _saving = true);
    final auth = AuthProviderScope.of(context);
    try {
      await auth.updateProfile({
        'firstName': _firstNameCtrl.text.trim(),
        'lastName': _lastNameCtrl.text.trim(),
        'city': _cityCtrl.text.trim(),
        'occupation': _occupationCtrl.text.trim(),
        'bio': _bioCtrl.text.trim(),
      });
      await auth.updateInterests(_selectedInterestIds.toList());
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Profile & interests updated')),
      );
    } catch (e) {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(e.toString())),
      );
    } finally {
      if (mounted) setState(() => _saving = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final auth = AuthProviderScope.of(context);

    return Scaffold(
      appBar: AppBar(title: const Text('Profile')),
      body: ListView(
        padding: const EdgeInsets.all(20),
        children: [
          NexBuddyTextField(controller: _firstNameCtrl, label: 'First Name'),
          const SizedBox(height: 12),
          NexBuddyTextField(controller: _lastNameCtrl, label: 'Last Name'),
          const SizedBox(height: 12),
          NexBuddyTextField(controller: _cityCtrl, label: 'City'),
          const SizedBox(height: 12),
          NexBuddyTextField(controller: _occupationCtrl, label: 'Occupation'),
          const SizedBox(height: 12),
          NexBuddyTextField(controller: _bioCtrl, label: 'Bio', maxLines: 3),
          const SizedBox(height: 20),
          const Text('Interests', style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          Wrap(
            spacing: 8,
            runSpacing: 8,
            children: auth.availableInterests.map((interest) {
              final selected = _selectedInterestIds.contains(interest.id);
              return FilterChip(
                label: Text(interest.name),
                selected: selected,
                onSelected: (val) {
                  setState(() {
                    if (val) {
                      _selectedInterestIds.add(interest.id);
                    } else {
                      _selectedInterestIds.remove(interest.id);
                    }
                  });
                },
              );
            }).toList(),
          ),
          const SizedBox(height: 20),
          NexBuddyButton(label: 'Save Profile', onPressed: _save, loading: _saving),
        ],
      ),
    );
  }
}
