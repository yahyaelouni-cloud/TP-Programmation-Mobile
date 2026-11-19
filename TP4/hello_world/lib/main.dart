import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

// Widget Racine de l'application (sans état)
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Hello World Flutter',
      home: HomePage(),
    );
  }
}

// Écran principal
class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Mon Premier Projet Flutter'),
      ),
      body: const Center(
        child: Text(
          'Hello World !',
          style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}
