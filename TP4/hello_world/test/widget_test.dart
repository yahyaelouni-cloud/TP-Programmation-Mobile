import 'package:flutter_test/flutter_test.dart';

import 'package:hello_world/main.dart';

void main() {
  testWidgets('Affiche Hello World', (WidgetTester tester) async {
    await tester.pumpWidget(const MyApp());
    expect(find.text('Hello World !'), findsOneWidget);
    expect(find.text('Mon Premier Projet Flutter'), findsOneWidget);
  });
}
