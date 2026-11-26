class InterestModel {
  final String id;
  final String name;
  final String category;
  final String? icon;

  InterestModel({
    required this.id,
    required this.name,
    required this.category,
    this.icon,
  });

  factory InterestModel.fromJson(Map<String, dynamic> json) {
    return InterestModel(
      id: json['id']?.toString() ?? '',
      name: json['name']?.toString() ?? '',
      category: json['category']?.toString() ?? '',
      icon: json['icon']?.toString(),
    );
  }
}
