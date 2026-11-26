import 'package:flutter_test/flutter_test.dart';
import 'package:nexbuddy_mobile/main.dart';

void main() {
  testWidgets('NexBuddy app renders splash screen', (WidgetTester tester) async {
    await tester.pumpWidget(NexBuddyApp());

    expect(find.text('NexBuddy'), findsOneWidget);
    expect(find.text('One Platform. Endless Connections.'), findsOneWidget);

    await tester.pump(const Duration(seconds: 1));
    await tester.pumpAndSettle();

    expect(find.text('Welcome to NexBuddy'), findsOneWidget);
  });
}
